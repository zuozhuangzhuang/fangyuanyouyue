package com.fangyuanyouyue.goods.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.fangyuanyouyue.goods.dao.*;
import com.fangyuanyouyue.goods.dto.GoodsCategoryDto;
import com.fangyuanyouyue.goods.dto.GoodsDto;
import com.fangyuanyouyue.goods.dto.SearchDto;
import com.fangyuanyouyue.goods.model.*;
import com.fangyuanyouyue.goods.param.GoodsParam;
import com.fangyuanyouyue.goods.service.GoodsInfoService;
import com.fangyuanyouyue.goods.service.SchedualUserService;
import com.fangyuanyouyue.goods.utils.DateStampUtils;
import com.fangyuanyouyue.goods.utils.ServiceException;
import com.github.pagehelper.PageHelper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service(value = "goodsInfoService")
public class GoodsInfoServiceImpl implements GoodsInfoService{
    @Autowired
    private GoodsInfoMapper goodsInfoMapper;
    @Autowired
    private GoodsImgMapper goodsImgMapper;
    @Autowired
    private GoodsCorrelationMapper goodsCorrelationMapper;
    @Autowired
    private GoodsCategoryMapper goodsCategoryMapper;
    @Autowired
    private GoodsCommentMapper goodsCommentMapperl;
    @Autowired
    private HotSearchMapper hotSearchMapper;
    @Autowired
    private BannerIndexMapper bannerIndexMapper;
    @Autowired
    private SchedualUserService schedualUserService;//调用其他service时用
    @Value("${pic_server:errorPicServer}")
    private String PIC_SERVER;// 图片服务器

    @Value("${pic_path:errorPicPath}")
    private String PIC_PATH;// 图片存放路径

    @Override
    public GoodsInfo selectByPrimaryKey(Integer id) {
        GoodsInfo goodsInfo = goodsInfoMapper.selectByPrimaryKey(id);
        if(goodsInfo == null){
            throw new SecurityException("获取商品失败！");
        }
        return goodsInfo;
    }

    @Override
    public List<GoodsDto> getGoodsInfoList(GoodsParam param) throws ServiceException{
        if(StringUtils.isNotEmpty(param.getSearch())){
            //TODO 搜索表
            HotSearch hotSearch = hotSearchMapper.selectByName(param.getSearch());
            if(hotSearch == null){
                hotSearch = new HotSearch();
                hotSearch.setAddTime(DateStampUtils.getTimesteamp());
                hotSearch.setUpdateTime(DateStampUtils.getTimesteamp());
                hotSearch.setName(param.getSearch());
                hotSearch.setCount(1);
                hotSearchMapper.insert(hotSearch);
            }else{
                hotSearch.setCount(hotSearch.getCount()+1);
                hotSearch.setUpdateTime(DateStampUtils.getTimesteamp());
                hotSearchMapper.updateByPrimaryKey(hotSearch);
            }
        }
        //分页
//        PageHelper.startPage(param.getStart(), param.getLimit());
        List<GoodsInfo> goodsInfos =goodsInfoMapper.getGoodsList(param.getUserId(),param.getStatus(),param.getSearch(),
                param.getPriceMin(),param.getPriceMax(),param.getSynthesize(),param.getQuality(),param.getStart(),param.getLimit(),param.getType());
        List<GoodsDto> goodsDtos = new ArrayList<>();
        for (GoodsInfo goodsInfo:goodsInfos) {
            goodsDtos.add(setDtoByGoodsInfo(goodsInfo));
        }
        return goodsDtos;
    }

    @Override
    public GoodsDto addGoods(Integer userId,String nickName,GoodsParam param) throws ServiceException {
        //商品表 goods_info
        GoodsInfo goodsInfo = new GoodsInfo();
        goodsInfo.setUserId(userId);
        goodsInfo.setName(param.getGoodsInfoName());
        goodsInfo.setDescription(param.getDescription());
        goodsInfo.setPrice(param.getPrice());
        goodsInfo.setPostage(param.getPostage());
        //排序：是否置顶
//        goodsInfo.setSort(param.getSort());
        if(StringUtils.isNotEmpty(param.getLabel())){
            goodsInfo.setLabel(param.getLabel());
        }
        goodsInfo.setType(param.getType());
        goodsInfo.setStatus(param.getStatus());
        goodsInfo.setAddTime(DateStampUtils.getTimesteamp());
        goodsInfo.setUpdateTime(DateStampUtils.getTimesteamp());
        goodsInfoMapper.insert(goodsInfo);

        //初始化商品分类关联表
        for(int i=0;i<param.getGoodsCategoryIds().length;i++){
            GoodsCorrelation goodsCorrelation = new GoodsCorrelation();
            goodsCorrelation.setGoodsId(goodsInfo.getId());
            goodsCorrelation.setAddTime(DateStampUtils.getTimesteamp());
            goodsCorrelation.setUpdateTime(DateStampUtils.getTimesteamp());
            goodsCorrelation.setGoodsCategoryId(param.getGoodsCategoryIds()[i]);
            goodsCorrelationMapper.insert(goodsCorrelation);
        }
        //商品图片表 goods_img
        //TODO 每个图片储存一条商品图片表信息
        for(int i=0;i<param.getImgUrls().length;i++){
            if(i == 0){
                saveGoodsPicOne(goodsInfo.getId(),param.getImgUrls()[i],param.getType(),1);
            }else{
                saveGoodsPicOne(goodsInfo.getId(),param.getImgUrls()[i],param.getType(),2);
            }
        }
        return setDtoByGoodsInfo(goodsInfo);
    }

    /**
     * 给GoodsDto赋值
     * @param goodsInfo
     * @return
     * @throws ServiceException
     */
    public GoodsDto setDtoByGoodsInfo(GoodsInfo goodsInfo) throws ServiceException{
        if(goodsInfo == null){
            throw new ServiceException("获取商品失败！");
        }else{
            List<GoodsImg> goodsImgs = goodsImgMapper.getImgsByGoodsId(goodsInfo.getId());
            List<GoodsCorrelation> goodsCorrelations = goodsCorrelationMapper.getCorrelationsByGoodsId(goodsInfo.getId());
            List<GoodsComment> goodsComments = goodsCommentMapperl.getCommentsByGoodsId(goodsInfo.getId());
            //获取卖家信息
            String verifyUser = schedualUserService.verifyUserById(goodsInfo.getUserId());
            JSONObject jsonObject = JSONObject.parseObject(verifyUser);
            JSONObject user = JSONObject.parseObject(jsonObject.getString("data"));
            GoodsDto goodsDto = new GoodsDto(user,goodsInfo,goodsImgs,goodsCorrelations,goodsComments);
            return goodsDto;
        }
    }
    /**
     * 添加商品图片
     * @param goodsId
     * @param type
     * @param sort
     * @throws ServiceException
     */
    public void saveGoodsPicOne(Integer goodsId, String imgUrl, Integer type,Integer sort) throws ServiceException{
        GoodsImg goodsImg = new GoodsImg();
        goodsImg.setAddTime(DateStampUtils.getTimesteamp());
        goodsImg.setGoodsId(goodsId);
        goodsImg.setType(type);//类型 1主图 2次图
        goodsImg.setSort(sort);
        goodsImg.setImgUrl(imgUrl);
        goodsImgMapper.insert(goodsImg);
    }

    @Override
    public void deleteGoods(Integer[] goodsIds) throws ServiceException {
        //批量修改商品状态为删除
        for(Integer goodsId:goodsIds){
            GoodsInfo goodsInfo = goodsInfoMapper.selectByPrimaryKey(goodsId);
            if(goodsInfo == null){
                throw new ServiceException("商品状态错误！");
            }else{
                goodsInfo.setStatus(5);//状态 普通商品 1出售中 2已售出 5删除
                goodsInfoMapper.updateByPrimaryKey(goodsInfo);
            }
        }
    }

    @Override
    public List<GoodsCategoryDto> categoryList() throws ServiceException{
        List<GoodsCategory> goodsCategories = goodsCategoryMapper.categoryParentList(null);
        List<GoodsCategoryDto> categoryDtos = GoodsCategoryDto.toDtoList(goodsCategories);
        for(GoodsCategoryDto categoryDto:categoryDtos){
            categoryDto.setChildList(GoodsCategoryDto.toDtoList(goodsCategoryMapper.getChildCategoryList(categoryDto.getCategoryId())));
        }
        return categoryDtos;
    }

    @Override
    public GoodsDto goodsInfo(Integer goodsId) throws ServiceException {
        GoodsInfo goodsInfo = goodsInfoMapper.selectByPrimaryKey(goodsId);
        return setDtoByGoodsInfo(goodsInfo);
    }

    @Override
    public List<GoodsDto> similarGoods(Integer goodsId,Integer pageNum, Integer pageSize) throws ServiceException {
        GoodsInfo goodsInfo = goodsInfoMapper.selectByPrimaryKey(goodsId);
        //根据商品
        if(goodsInfo == null){
            throw new ServiceException("获取商品失败！");
        }else{
            //获取商品的分类集合
            List<GoodsCorrelation> goodsCorrelations = goodsCorrelationMapper.getCorrelationsByGoodsId(goodsId);
            List<Integer> goodsIds = new ArrayList<>();
            for(GoodsCorrelation goodsCorrelation:goodsCorrelations){
                goodsIds.add(goodsCorrelation.getGoodsId());
            }
            PageHelper.startPage(pageNum, pageSize);
            List<GoodsInfo> goodsInfos = goodsInfoMapper.getGoodsByGoodsIds(goodsIds,pageNum,pageSize);
            List<GoodsDto> goodsDtos = new ArrayList<>();
            for(GoodsInfo model:goodsInfos){
                goodsDtos.add(setDtoByGoodsInfo(model));
            }
            return goodsDtos;
        }
    }

    @Override
    public List<BannerIndex> getBanner(Integer type) throws ServiceException {
        List<BannerIndex> banner = bannerIndexMapper.getBanner(type);
        return banner;
    }

    @Override
    public BannerIndex addBanner(GoodsParam param) throws ServiceException {
        BannerIndex bannerIndex = new BannerIndex();
        bannerIndex.setAddTime(DateStampUtils.getTimesteamp());
        bannerIndex.setUpdateTime(DateStampUtils.getTimesteamp());
        bannerIndex.setType(param.getType());
        bannerIndex.setBusinessId(param.getBusinessId());
        bannerIndex.setImgUrl(param.getImgUrl());
        bannerIndex.setJumpType(param.getJumpType());
        if(StringUtils.isNotEmpty(param.getTitle())){
            bannerIndex.setTitle(param.getTitle());
        }
        if(param.getSort() != null){
            bannerIndex.setSort(param.getSort());
        }
        bannerIndex.setStatus(0);//是否下架，0未下架 1下架
        bannerIndexMapper.insert(bannerIndex);
        return bannerIndex;
    }

    @Override
    public BannerIndex updateBanner(GoodsParam param) throws ServiceException {
        BannerIndex bannerIndex = bannerIndexMapper.selectByPrimaryKey(param.getBannerIndexId());
        if(bannerIndex == null){
            throw new ServiceException("获取轮播图失败！");
        }else{
            bannerIndex.setUpdateTime(DateStampUtils.getTimesteamp());
            bannerIndex.setType(param.getType());
            bannerIndex.setBusinessId(param.getBusinessId());
            bannerIndex.setImgUrl(param.getImgUrl());
            bannerIndex.setJumpType(param.getJumpType());
            if(StringUtils.isNotEmpty(param.getTitle())){
                bannerIndex.setTitle(param.getTitle());
            }
            if(param.getSort() != null){
                bannerIndex.setSort(param.getSort());
            }
            if(param.getStatus() != null){
                bannerIndex.setStatus(param.getStatus());
            }
            bannerIndexMapper.updateByPrimaryKey(bannerIndex);
            return bannerIndex;
        }
    }

    @Override
    public List<SearchDto> hotSearch() throws ServiceException {
        PageHelper.startPage(0,10);
        List<HotSearch> hotSearchList = hotSearchMapper.getHotSearchList();
        List<SearchDto> searchDtos = SearchDto.toDtoList(hotSearchList);
        return searchDtos;
    }
}
