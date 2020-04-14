package com.baizhi.gxy;

import com.baizhi.gxy.entity.Emp;
import com.baizhi.gxy.entity.Video;
import com.baizhi.gxy.repository.EmpRepository;
import com.baizhi.gxy.repository.VideoRepository;
import com.baizhi.gxy.service.VideoService;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.SearchResultMapper;
import org.springframework.data.elasticsearch.core.aggregation.AggregatedPage;
import org.springframework.data.elasticsearch.core.aggregation.impl.AggregatedPageImpl;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class EsTests {

    @Resource
    EmpRepository empRepository;

    @Resource
    ElasticsearchTemplate elasticsearchTemplate;

    @Resource
    VideoRepository videoRepository;

    @Resource
    VideoService videoService;


    /*
     *   Redis
     *   Es
     *   Mysql
     *   阿里云
     *
     *  添加
     *    数据库mysql
     *    缓存  redis
     *    索引 Es
     *    文件  阿里云
     *
     *   删除
     * */
    @Test
    public void querySearchVideosss() {

        String content = "橘子";

        //处理高亮的操作
        HighlightBuilder.Field field = new HighlightBuilder.Field("*");
        field.preTags("<span style='color:red'>"); //前缀
        field.postTags("</span>"); //后缀

        //查询的条件
        NativeSearchQuery nativeSearchQuery = new NativeSearchQueryBuilder()
                .withIndices("yingxv")  //指定索引名
                .withTypes("video")   //指定索引类型
                .withQuery(QueryBuilders.queryStringQuery(content).field("title").field("brief"))  //搜索的条件
                .withHighlightFields(field)  //处理高亮
                //.withFields("title","brief","cover")  //查询返回指定字段
                .build();

        //高亮查询
        AggregatedPage<Video> videoList = elasticsearchTemplate.queryForPage(nativeSearchQuery, Video.class, new SearchResultMapper() {
            @Override
            public <T> AggregatedPage<T> mapResults(SearchResponse searchResponse, Class<T> aClass, Pageable pageable) {

                ArrayList<Video> videos = new ArrayList<>();

                //获取查询结果
                SearchHit[] hits = searchResponse.getHits().getHits();

                for (SearchHit hit : hits) {

                    //System.out.println("====="+hit.getSourceAsMap()); //原始数据
                    //System.out.println("-------"+hit.getHighlightFields()); //高亮数据

                    //原始数据
                    Map<String, Object> map = hit.getSourceAsMap();

                    //处理普通数据

                    String id = map.get("id") != null ? map.get("id").toString() : null;
                    String title = map.get("title") != null ? map.get("title").toString() : null;
                    String brief = map.get("brief") != null ? map.get("brief").toString() : null;
                    String path = map.get("path") != null ? map.get("path").toString() : null;
                    String cover = map.get("cover") != null ? map.get("cover").toString() : null;
                    String categoryId = map.get("categoryId") != null ? map.get("categoryId").toString() : null;
                    String groupId = map.get("groupId") != null ? map.get("groupId").toString() : null;
                    String userId = map.get("userId") != null ? map.get("userId").toString() : null;

                    //处理日期操作
                    Date date = null;

                    if (map.get("publishDate") != null) {
                        String publishDateStr = map.get("publishDate").toString();

                        //处理日期转换
                        Long aLong = Long.valueOf(publishDateStr);
                        date = new Date(aLong);
                    }

                    //封装video对象
                    Video video = new Video(id, title, brief, path, cover, date, categoryId, groupId, userId);

                    //处理高亮数据
                    Map<String, HighlightField> highlightFields = hit.getHighlightFields();

                    if (title != null) {
                        if (highlightFields.get("title") != null) {
                            String titles = highlightFields.get("title").fragments()[0].toString();
                            video.setTitle(titles);
                        }
                    }

                    if (brief != null) {
                        if (highlightFields.get("brief") != null) {
                            String briefs = highlightFields.get("brief").fragments()[0].toString();
                            video.setBrief(briefs);
                        }
                    }

                    //将对象放入集合
                    videos.add(video);

                }
                //强转 返回
                return new AggregatedPageImpl<T>((List<T>) videos);
            }
        });

        videoList.forEach(video -> System.out.println(video));

    }


    @Test
    public void querySearchVideos() {

        String content = "橘子";

        //处理高亮的操作
        HighlightBuilder.Field field = new HighlightBuilder.Field("*");
        field.preTags("<span style='color:red'>"); //前缀
        field.postTags("</span>"); //后缀

        //查询的条件
        NativeSearchQuery nativeSearchQuery = new NativeSearchQueryBuilder()
                .withIndices("yingxv")  //指定索引名
                .withTypes("video")   //指定索引类型
                .withQuery(QueryBuilders.queryStringQuery(content).field("title").field("brief"))  //搜索的条件
                .withHighlightFields(field)  //处理高亮
                //.withFields("title","brief","cover")  //查询返回指定字段
                .build();

        //高亮查询
        AggregatedPage<Video> videoList = elasticsearchTemplate.queryForPage(nativeSearchQuery, Video.class, new SearchResultMapper() {
            @Override
            public <T> AggregatedPage<T> mapResults(SearchResponse searchResponse, Class<T> aClass, Pageable pageable) {

                ArrayList<Video> videos = new ArrayList<>();


                //获取查询结果
                SearchHit[] hits = searchResponse.getHits().getHits();

                for (SearchHit hit : hits) {

                    //System.out.println("====="+hit.getSourceAsMap()); //原始数据
                    //System.out.println("-------"+hit.getHighlightFields()); //高亮数据

                    //原始数据
                    Map<String, Object> map = hit.getSourceAsMap();

                    //处理普通数据

                    String id = map.get("id") != null ? map.containsKey("id") ? map.get("id").toString() : null : null;
                    String title = map.get("title") != null ? map.containsKey("title") ? map.get("title").toString() : null : null;
                    String brief = map.get("brief") != null ? map.containsKey("brief") ? map.get("brief").toString() : null : null;
                    String path = map.get("path") != null ? map.containsKey("path") ? map.get("path").toString() : null : null;
                    String cover = map.get("cover") != null ? map.containsKey("cover") ? map.get("cover").toString() : null : null;
                    String categoryId = map.get("categoryId") != null ? map.containsKey("categoryId") ? map.get("categoryId").toString() : null : null;
                    String groupId = map.get("groupId") != null ? map.containsKey("groupId") ? map.get("groupId").toString() : null : null;
                    String userId = map.get("userId") != null ? map.containsKey("userId") ? map.get("userId").toString() : null : null;

                    //处理日期操作
                    Date date = null;

                    if (map.get("publishDate") != null) {
                        if (map.containsKey("publishDate")) {

                            String publishDateStr = map.get("publishDate").toString();

                            //处理日期转换
                            Long aLong = Long.valueOf(publishDateStr);
                            date = new Date(aLong);
                        }
                    }

                    //封装video对象
                    Video video = new Video(id, title, brief, path, cover, date, categoryId, groupId, userId);

                    //处理高亮数据
                    Map<String, HighlightField> highlightFields = hit.getHighlightFields();

                    if (title != null) {
                        if (highlightFields.get("title") != null) {
                            String titles = highlightFields.get("title").fragments()[0].toString();
                            video.setTitle(titles);
                        }
                    }

                    if (brief != null) {
                        if (highlightFields.get("brief") != null) {
                            String briefs = highlightFields.get("brief").fragments()[0].toString();
                            video.setBrief(briefs);
                        }

                    }

                    //将对象放入集合
                    videos.add(video);

                }
                //强转 返回
                return new AggregatedPageImpl<T>((List<T>) videos);
            }
        });

        videoList.forEach(video -> System.out.println(video));

    }

    @Test
    public void querySearchVideoss() {

        String content = "橘子";

        //处理高亮的操作
        HighlightBuilder.Field field = new HighlightBuilder.Field("*");
        field.preTags("<span style='color:red'>"); //前缀
        field.postTags("</span>"); //后缀


        //查询的条件
        NativeSearchQuery nativeSearchQuery = new NativeSearchQueryBuilder()
                .withIndices("yingxv")  //指定索引名
                .withTypes("video")   //指定索引类型
                .withQuery(QueryBuilders.queryStringQuery(content).field("title").field("brief"))  //搜索的条件
                .withHighlightFields(field)  //处理高亮
                .withFields("title", "brief", "cover")  //查询返回指定字段
                .build();

        //高亮查询
        elasticsearchTemplate.queryForPage(nativeSearchQuery, Video.class, new SearchResultMapper() {
            @Override
            public <T> AggregatedPage<T> mapResults(SearchResponse searchResponse, Class<T> aClass, Pageable pageable) {

                ArrayList<Video> videos = new ArrayList<>();


                //获取查询结果
                SearchHit[] hits = searchResponse.getHits().getHits();

                for (SearchHit hit : hits) {

                    //System.out.println("====="+hit.getSourceAsMap()); //原始数据
                    //System.out.println("-------"+hit.getHighlightFields()); //高亮数据

                    //原始数据
                    Map<String, Object> map = hit.getSourceAsMap();

                    //处理普通数据

                    String id = map.containsKey("id") ? map.get("id").toString() : null;
                    String title = map.containsKey("title") ? map.get("title").toString() : null;
                    String brief = map.containsKey("brief") ? map.get("brief").toString() : null;
                    String path = map.containsKey("path") ? map.get("path").toString() : null;
                    String cover = map.containsKey("cover") ? map.get("cover").toString() : null;
                    String categoryId = map.containsKey("categoryId") ? map.get("categoryId").toString() : null;
                    String groupId = map.containsKey("groupId") ? map.get("groupId").toString() : null;
                    String userId = map.containsKey("userId") ? map.get("userId").toString() : null;


                    Date date = null;
                    if (map.containsKey("publishDate")) {
                        String publishDateStr = map.get("publishDate").toString();

                        //处理日期转换
                        Long aLong = Long.valueOf(publishDateStr);
                        date = new Date(aLong);
                    }


                    /*String id = map.get("id").toString();
                    String title = map.get("title").toString();
                    String brief = map.get("brief").toString();
                    String path = map.get("path").toString();
                    String cover = map.get("cover").toString();

                    //处理日期操作
                    String publishDateStr = map.get("publishDate").toString();

                    Long aLong = Long.valueOf(publishDateStr);
                    Date date = new Date(aLong);*/


                    /*SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh-mm-ss");

                    Date publishDate = null;

                    try {
                        publishDate = simpleDateFormat.parse(publishDateStr);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }*/

                    /*String categoryId = map.get("categoryId").toString();
                    String groupId = map.get("groupId").toString();
                    String userId = map.get("userId").toString();*/

                    //封装video对象
                    Video video = new Video(id, title, brief, path, cover, date, categoryId, groupId, userId);

                    System.out.println(video);

                    //处理高亮数据
                    Map<String, HighlightField> highlightFields = hit.getHighlightFields();


                    if (highlightFields.get("title") != null) {
                        String titles = highlightFields.get("title").fragments()[0].toString();
                        video.setTitle(titles);
                    }

                    if (highlightFields.get("brief") != null) {
                        String briefs = highlightFields.get("brief").fragments()[0].toString();
                        video.setBrief(briefs);
                    }

                    System.out.println("===" + video);


                    //将对象放入集合
                    videos.add(video);

                    /*
                    *
                    ====={brief=橘子啊橘子, cover=null, path=C:\fakepath\树叶.mp4, groupId=1, publishDate=2020-04-10, id=fad4993b-0f67-4151-bc8f-3dde5c8b2be6, title=橘子, userId=1, categoryId=1}

                    -------{
                    * brief=[brief],
                    * fragments[[<span style='color:red'>橘子</span>啊<span style='color:red'>橘子</span>]],
                    * title=[title],
                    * fragments[[<span style='color:red'>橘子</span>]]}

                    * ====={brief=石头和橘子, cover=null, path=C:\fakepath\2020宣传视频.mp4, groupId=1, publishDate=2020-04-10, id=02d3ec1c-5e59-49c6-a132-7ebb6e94cbbb, title=石头橘子, userId=1, categoryId=1}
                    -------{brief=[brief], fragments[[石头和<span style='color:red'>橘子</span>]],
                    * title=[title], fragments[[石头<span style='color:red'>橘子</span>]]}

                    * ====={brief=我是一棵小石头,静静的埋在泥土之中, cover=2.jpg, path=2.mp4, groupId=1, publishDate=1586502731823, id=3, title=小橘子, userId=1, categoryId=2}
                    -------{title=[title], fragments[[小<span style='color:red'>橘子</span>]]}
                    * */
                }
                return null;
            }
        });
    }

    @Test
    public void querySearchVideo() {

        String content = "石头";

        //查询的条件
        NativeSearchQuery nativeSearchQuery = new NativeSearchQueryBuilder()
                .withIndices("yingxv")  //指定索引名
                .withTypes("video")   //指定索引类型
                .withQuery(QueryBuilders.queryStringQuery(content).field("title").field("brief"))  //搜索的条件
                .build();

        //查询
        List<Video> videos = elasticsearchTemplate.queryForList(nativeSearchQuery, Video.class);

        videos.forEach(video -> System.out.println(video));
    }

    @Test
    public void saveVideo() {

        /*HashMap<String, Object> map = videoService.queryByPage(1, 100);
        List<Video> rows = (List<Video>) map.get("rows");
        for (Video row : rows) {
            System.out.println(row);
            videoRepository.save(row);
        }*/
        videoRepository.save(new Video("35", "小橘子s石头", "我是一棵小石头,静静的埋在泥土之中s", null, "2.jpg", new Date(), "2", "1", "1"));
    }

    @Test
    public void querySearch() {

        String content = "橘子";

        //查询的条件
        NativeSearchQuery nativeSearchQuery = new NativeSearchQueryBuilder()
                .withIndices("yingxs")  //指定索引名
                .withTypes("emp")   //指定索引类型
                .withQuery(QueryBuilders.queryStringQuery(content).field("name"))  //搜索的条件
                .build();

        //查询
        List<Emp> emps = elasticsearchTemplate.queryForList(nativeSearchQuery, Emp.class);

        emps.forEach(emp -> System.out.println(emp));
    }

    @Test
    public void save() {
        Emp emp = new Emp("5", "小橘子橘子石头", 30, new Date());
        empRepository.save(emp);
    }

    @Test
    public void delete() {
        Emp emp = new Emp("2", "小石头石头", 20, new Date());
        empRepository.delete(emp);
    }


    @Test
    public void query() {
        Iterable<Emp> all = empRepository.findAll();
        all.forEach(emp -> System.out.println(emp));
        //Sort.by(Sort.Order.asc("price"))
    }

    @Test
    public void querySort() {
        Iterable<Emp> all = empRepository.findAll(Sort.by(Sort.Order.asc("age")));
        all.forEach(emp -> System.out.println(emp));
    }

    @Test
    public void queryPage() {
        Iterable<Emp> all = empRepository.findAll(PageRequest.of(0, 2));
        all.forEach(emp -> System.out.println(emp));
    }

    @Test
    public void queryById() {

        Optional<Emp> emp = empRepository.findById("1");
        System.out.println(emp);
    }

    @Test
    public void queryByName() {
        List<Emp> emps = empRepository.findByName("小石头");
        emps.forEach(emp -> System.out.println(emp));
    }
}
