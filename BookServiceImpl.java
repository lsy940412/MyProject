package com.itany.bookservice.serviceImpl;

import com.alibaba.dubbo.config.annotation.Service;
import com.itany.bookapi.BookService;
import com.itany.bookservice.dao.BookMapper;
import com.itany.pojo.Book;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@Service
public class BookServiceImpl implements BookService {

    @Autowired
    private HttpSolrClient solrClient;
    @Autowired
    private BookMapper bookMapper;


    @Override
    public void addDoc() {
        System.out.println("----service---");
        List<Book> bookList = bookMapper.selectByIndex();
        System.out.println(bookList+"----booklist---");
        try {
        for (Book book:bookList){
            SolrInputDocument doc = new SolrInputDocument();
            doc.addField("id",book.getId());
            doc.addField("bookName",book.getBookName());
            doc.addField("author",book.getAuthor());
            doc.addField("publishDate",book.getPublishDate());
            doc.addField("groupPrice",book.getGroupPrice());
            doc.addField("price",book.getPrice());
            doc.addField("imgUrl",book.getImgurl());
            doc.addField("bookIntroduce",book.getBookIntroduce());
            doc.addField("pressName",book.getPress().getName());

            solrClient.add(doc);
        }
            solrClient.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }



    }

    @Override
    public Map<String,Object> findDoc(String index,String pageNo) {
        Map<String,Object> m = new HashMap<>();
        List<Map<String,String>> list = new ArrayList<Map<String,String>>();
        if(pageNo == null || pageNo.equals("")){
            pageNo = "1";
        }
        try {
            String q ="bookName:"+index+" OR "+"author:"+index+" OR "+"bookIntroduce:"+index+" OR "+"pressName:"+index;
            SolrQuery query = new SolrQuery();
            query.setQuery(q);
            query.setStart((Integer.parseInt(pageNo)-1)*3);
            query.setRows(3);
            query.setHighlight(true);
            query.setHighlightSimplePre("<font color='red'>");
            query.setHighlightSimplePost("</font>");
            query.setParam("hl.fl","*");
            QueryResponse response = solrClient.query(query);
            SolrDocumentList results = response.getResults();
            long total =  results.getNumFound();
            long pageCount;
            if((total%3) == 0 ){
                pageCount = total/3;
            }else {
                pageCount = total/3+1;
            }


            m.put("pageNo",pageNo);
            m.put("rows","3");
            m.put("pageCount", String.valueOf(pageCount) == null?"1":String.valueOf(pageCount));
            for(int i=0;i<results.size();i++){
                Map<String,String> vo = new HashMap<String,String>();
                SolrDocument doc = results.get(i);
                String id = (String)doc.get("id");
                //获取高亮数据
                Map<String,List<String>> map = response.getHighlighting().get(id);

                List<String> userlist = map.get("bookName");
                if(null != userlist){
                    vo.put("bookName",userlist.get(0));
                }else{
                    vo.put("bookName",(String)doc.get("bookName"));
                }

                List<String> userlist1 = map.get("author");
                if(null != userlist1){
                    vo.put("author",userlist1.get(0));
                }else{
                    vo.put("author",(String)doc.get("author"));
                }

                List<String> userlist2 = map.get("publishDate");
                if(null != userlist2){
                    vo.put("publishDate",userlist2.get(0));
                }else{
                    vo.put("publishDate",(String)doc.get("publishDate"));
                }

                List<String> userlist3 = map.get("bookIntroduce");

                if(null != userlist3){
                    vo.put("bookIntroduce",userlist3.get(0));
                }else{
                    vo.put("bookIntroduce",(String)doc.get("bookIntroduce"));
                }

                List<String> userlist4 = map.get("groupPrice");
                if(null != userlist4){
                    vo.put("groupPrice",userlist4.get(0));
                }else{
                    vo.put("groupPrice",(String)doc.get("groupPrice"));
                }

                List<String> userlist5 = map.get("price");
                if(null != userlist5){
                    vo.put("price",userlist5.get(0));
                }else{
                    vo.put("price",(String)doc.get("price"));
                }

                List<String> userlist6= map.get("pressName");
                if(null != userlist6){
                    vo.put("pressName",userlist6.get(0));
                }else{
                    vo.put("pressName",(String)doc.get("pressName"));
                }
                List<String> userlist7= map.get("imgUrl");
                if(null != userlist7){
                    vo.put("imgUrl",userlist6.get(0));
                }else{
                    vo.put("imgUrl",(String)doc.get("imgUrl"));
                }

                list.add(vo);


            }
            m.put("list",list);
        }catch (Exception e){
            e.printStackTrace();
        }

        System.out.print("----m---"+(String) m.get("pageNo")+"-----"+m.get("pageCount")+m.get("list")+"----");
        return m;
    }


}
