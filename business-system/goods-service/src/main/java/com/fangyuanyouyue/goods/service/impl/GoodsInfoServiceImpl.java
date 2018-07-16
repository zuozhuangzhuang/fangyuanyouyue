package com.fangyuanyouyue.goods.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.fangyuanyouyue.goods.dao.*;
import com.fangyuanyouyue.goods.dto.GoodsCategoryDto;
import com.fangyuanyouyue.goods.dto.GoodsCommentDto;
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
import java.util.Map;

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
                param.getPriceMin(),param.getPriceMax(),param.getSynthesize(),param.getQuality(),param.getStart(),param.getLimit(),param.getType(),param.getGoodsCategoryIds());
        //分类热度加一
        if(param.getGoodsCategoryIds() != null && param.getGoodsCategoryIds().length>0){
            goodsCategoryMapper.addSearchCountByCategoryIds(param.getGoodsCategoryIds());
        }
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
        if(param.getType() == 2){
            goodsInfo.setFloorPrice(param.getFloorPrice());
            goodsInfo.setIntervalTime(param.getIntervalTime());
            goodsInfo.setMarkdown(param.getMarkdown());
        }
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
    private GoodsDto setDtoByGoodsInfo(GoodsInfo goodsInfo) throws ServiceException{
        if(goodsInfo == null){
            throw new ServiceException("获取商品失败！");
        }else{
            List<GoodsImg> goodsImgs = goodsImgMapper.getImgsByGoodsId(goodsInfo.getId());
            List<GoodsCorrelation> goodsCorrelations = goodsCorrelationMapper.getCorrelationsByGoodsId(goodsInfo.getId());
            //按照先后顺序获取评论
            List<Map<String, Object>> maps = goodsCommentMapperl.selectMapByGoodsIdCommentId(null,goodsInfo.getId(), 0, 3);
            List<GoodsCommentDto> goodsCommentDtos = GoodsCommentDto.mapToDtoList(maps);
            for(GoodsCommentDto goodsCommentDto:goodsCommentDtos){
                Map<String, Object> map = goodsCommentMapperl.selectByCommentId(goodsCommentDto.getCommentId());
                if(map != null){
                    goodsCommentDto.setToUserId((Integer)map.get("user_id"));
                    goodsCommentDto.setToUserHeadImgUrl((String)map.get("head_img_url"));
                    goodsCommentDto.setToUserName((String)map.get("nick_name"));
                }
            }
//            if(goodsCommentDtos != null){
//                circulation:for(GoodsComment goodsComment:goodsComments){
//                    goodsCommentDtos.add(new GoodsCommentDto(goodsComment));
//                    if(goodsCommentDtos.size() == 3){
//                        break;
//                    }
//                    //获取评论的回复
//                    List<GoodsComment> replys = goodsCommentMapperl.selectByGoodsIdCommentId(goodsComment.getCommentId(),goodsInfo.getId());
//                    if(replys == null){
//                        //评论没有回复，就获取下一条商品的评论
//                        continue;
//                    }else{
//                        //评论有回复，将回复放到评论的replys中
//                        List<GoodsCommentDto> replyDtos = new ArrayList<>();
//                        for(int i=0;i<replys.size();i++){
//                            GoodsCommentDto replyDto = new GoodsCommentDto(replys.get(i));
//                            replyDtos.add(new GoodsCommentDto());
//                            goodsCommentDtos.add(new GoodsCommentDto(replys.get(i)));
//                            if(goodsCommentDtos.size() == 3){
//                                break circulation;
//                            }
//                        }
//                    }
//                }
//            }
            //获取卖家信息
            String verifyUser = schedualUserService.verifyUserById(goodsInfo.getUserId());
            JSONObject jsonObject = JSONObject.parseObject(verifyUser);
            JSONObject user = JSONObject.parseObject(jsonObject.getString("data"));
            GoodsDto goodsDto = new GoodsDto(user,goodsInfo,goodsImgs,goodsCorrelations,goodsCommentDtos);
            goodsDto.setCommentCount(goodsCommentMapperl.selectCount(goodsInfo.getId()));
            return goodsDto;
        }
    }

    /**
     * 根据商品ID和评论ID获取评论列表
     * @param commentId
     * @param goodsId
     * @return
     */
//    public List<GoodsComment> getCommentsByGoodsId(Integer commentId,Integer goodsId) throws ServiceException{
//        List<GoodsComment> goodsComments;
//        if(commentId == null){
//            //商品的最新评论
//            goodsComments = goodsCommentMapperl.selectByGoodsId(goodsId);
//            return goodsComments;
//        }else{
//            //评论的最新回复
//            goodsComments = goodsCommentMapperl.selectByGoodsIdCommentId(commentId,goodsId);
//            if(goodsComments == null){
//                //没有回复
//                return null;
//            }else{
//                goodsCommentMapperl.selectByGoodsIdCommentId(commentId,goodsId);
//                return goodsComments;
//            }
//        }
//    }
    /**
     * 添加商品图片路径
     * @param goodsId
     * @param type
     * @param sort
     * @throws ServiceException
     */
    private void saveGoodsPicOne(Integer goodsId, String imgUrl, Integer type,Integer sort) throws ServiceException{
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

    @Override
    public List<GoodsCategoryDto> hotCategary() throws ServiceException {
        PageHelper.startPage(0,10);
        List<GoodsCategory> hotCategaryList = goodsCategoryMapper.getHotCategaryList();
        List<GoodsCategoryDto> goodsCategoryDtos = GoodsCategoryDto.toDtoList(hotCategaryList);
        return goodsCategoryDtos;
    }
}
