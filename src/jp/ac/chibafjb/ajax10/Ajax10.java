package jp.ac.chibafjb.ajax10;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.arnx.jsonic.JSON;

class UserData
{
	public String cmd;
	public String name;
}

class PageNo
{
	public int id;
	public String scenario;
}



@WebServlet("/Ajax10")
public class Ajax10 extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private jp.ac.chibafjb.ajax10.Oracle mOracle;
	private final String DB_ID = "x14g020";
	private final String DB_PASS = "furutama0811";

    /**
     * @see HttpServlet#HttpServlet()
     */
    public Ajax10() {
        super();
        // TODO Auto-generated constructor stub
    }
	public void init() throws ServletException {
		// TODO 自動生成されたメソッド・スタブ
		super.init();


		try{
			mOracle = new Oracle();
			mOracle.connect("ux4", DB_ID, DB_PASS);

			//テーブルが無ければ作成

			if(!mOracle.isTable("User_DB")&& !mOracle.isTable("Scenario_DB"))

			{
				mOracle.execute("create table User_DB(name varchar2(10),id_2 number)");
				mOracle.execute("create table Scenario_DB (id_1 number,scenario varchar2(4000))");
			}
		} catch (Exception e) {
			System.err.println("認証に失敗しました");
		}
	}

	@Override
	public void destroy() {
		//DB切断
		mOracle.close();
		// TODO 自動生成されたメソッド・スタブ
		super.destroy();
	}
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		action(request,response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		action(request,response);
	}
	private void action(HttpServletRequest request, HttpServletResponse response) throws IOException {
		//出力ストリームの作成
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/plain; charset=UTF-8");
        PrintWriter out = response.getWriter();

    //データ受け取り
        UserData userData = JSON.decode(request.getInputStream(),UserData.class);

        //はじめからのシナリオ読み込み
        if(userData.cmd.equals("read1"))
        {
            	ArrayList<PageNo> list = new ArrayList<PageNo>();
    			//DBからシナリオ読み込み
    			ResultSet res = mOracle.query("select * from scenario_db where id_l = 1");
    			try {
					while(res.next())
					{
						PageNo pageNo = new PageNo();
						pageNo.id = res.getInt(1);
						pageNo.scenario = res.getString(2);
						list.add(pageNo);
					}
	    			//JSON形式に変換
	                String json = JSON.encode(list);
	                //出力
	                out.println(json);
				} catch (SQLException e) {
					// TODO 自動生成された catch ブロック
					e.printStackTrace();
				}
    		}
	}
}

//
//
//
//
//        }else if(recvData.cmd.equals("read2"))
//        {
//        	 KijiSend kijiSend = new KijiSend();
//            //記事内容一覧
//            //記事の受け取り＆送信処理
//            IchiranRecv ichiranRecv = recvData.ichiranRecv;
//           // if("write".equals(ichiranRecv.id))
//            {
//            	String sql = String.format("select * from db_kigi where id = '%d' ",ichiranRecv.id);
//            	ResultSet res = mOracle.query(sql);
//            	try {
//					if(res.next())
//					{
//
//						kijiSend.title = res.getString(2);
//						kijiSend.news = res.getString(3);
//
//					}
//					res.close();
//				} catch (JSONException e) {
//					// TODO 自動生成された catch ブロック
//					e.printStackTrace();
//				} catch (SQLException e) {
//					// TODO 自動生成された catch ブロック
//					e.printStackTrace();
//				}
//
//            }
//
//            try {
//    			//データの送信処理
//    			String sql = String.format("select * from db_exam where kiji_id = '%d'  order by com_id ",ichiranRecv.id);
//    			ResultSet res = mOracle.query(sql);
//    			while(res.next())
//    			{
//    				SendData sendData = new SendData();
//    				sendData.id = res.getInt(2);
//    				sendData.name = res.getString(3);
//    				sendData.msg = res.getString(4);
//    				kijiSend.list.add(sendData);
//    			}
//    			//JSON形式に変換
//    	          String json2 = JSON.encode(kijiSend);
//    	          //出力
//    	            out.println(json2);
//    			} catch (SQLException e) {
//    				e.printStackTrace();
//    			}
//
//        }else if(recvData.cmd.equals("read3"))
//    	{
//        	IchiranRecv ichiranRecv = recvData.ichiranRecv;
//        	KijiSend kijiSend = new KijiSend();
//		//データの受け取り処理
//	       try {
//				if(recvData.recv != null )
//				{
//					//RecvData ichiranRecv = new RecvData();
//					//IchiranRecv ichiranRecv = recvData.ichiranRecv;
//					//書き込み処理
//					String sql = String.format("insert into db_exam values('%d',db_exam_seq.nextval,'%s','%s')",
//							ichiranRecv.id,recvData.recv.name,recvData.recv.msg);
//					mOracle.execute(sql);
//				}
//   			//データの送信処理
//   			String sql = String.format("select * from db_exam where kiji_id = '%d'  order by com_id ",ichiranRecv.id);
//   			ResultSet res = mOracle.query(sql);
//   			while(res.next())
//   			{
//   				SendData sendData = new SendData();
//   				sendData.id = res.getInt(1);
//   				sendData.name = res.getString(3);
//   				sendData.msg = res.getString(4);
//   				kijiSend.list.add(sendData);
//   			}
//   			//JSON形式に変換
//   	          String json2 = JSON.encode(kijiSend);
//   	          //出力
//   	            out.println(json2);
//   			} catch (SQLException e) {
//   				e.printStackTrace();
//   			}
//
//
//	     /* try {
//			//データの送信処理
//	    	  IchiranRecv ichiranRecv = recvData.ichiranRecv;
//			ArrayList<SendData> list2 = new ArrayList<SendData>();
//			String sql = String.format("select * from db_exam where kiji_id = '%d'  order by id ",ichiranRecv.id);
//			ResultSet res = mOracle.query(sql);
//			while(res.next())
//			{
//				SendData sendData = new SendData();
//				sendData.id = res.getInt(1);
//				sendData.name = res.getString(2);
//				sendData.msg = res.getString(3);
//				list2.add(sendData);
//			}
//			//JSON形式に変換
//	          String json2 = JSON.encode(list2);
//	          //出力
//	            out.println(json2);
//			} catch (SQLException e) {
//				e.printStackTrace();
//			}
//*/
//		}
