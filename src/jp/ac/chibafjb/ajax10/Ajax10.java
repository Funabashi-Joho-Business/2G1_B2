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

class UserData {
	public String cmd;
	public String name;
	public int id;
	// public int senario;
}

class PageNo {
	public int id;
	public String scenario;
}

@WebServlet("/Ajax10")
public class Ajax10 extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private jp.ac.chibafjb.ajax10.Oracle mOracle;
	private final String DB_ID = "x14g020";
	private final String DB_PASS = "furutama0811";
	public int senario;
	public int q;

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
		senario = 1;

		try {
			mOracle = new Oracle();
			mOracle.connect("ux4", DB_ID, DB_PASS);

			// テーブルが無ければ作成

			if (!mOracle.isTable("User_DB") && !mOracle.isTable("Scenario_DB"))

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
		// DB切断
		mOracle.close();
		// TODO 自動生成されたメソッド・スタブ
		super.destroy();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		action(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		action(request, response);
	}

	private void action(HttpServletRequest request, HttpServletResponse response) throws IOException {
		// 出力ストリームの作成
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/plain; charset=UTF-8");
		PrintWriter out = response.getWriter();

		// データ受け取り
		UserData userData = JSON.decode(request.getInputStream(), UserData.class);

		ArrayList<PageNo> list = new ArrayList<PageNo>();

		if (userData.cmd.equals("read1")) {
				// userData.senario = 1;
				senario = 1;
				// DBからシナリオ読み込み
				// はじめからのシナリオ読み込み

				String sql = String.format(
						"select * from scenario_db where id_l = '%d'",senario);
				ResultSet res = mOracle.query(sql);
				try {
					while (res.next()) {
						PageNo pageNo = new PageNo();
						pageNo.id = res.getInt(1);
						pageNo.scenario = res.getString(2);
						list.add(pageNo);
					}
					// JSON形式に変換
					String json = JSON.encode(list);
					// 出力
					out.println(json);
				} catch (SQLException e) {
					// TODO 自動生成された catch ブロック
					e.printStackTrace();
				}
		}else if (userData.cmd.equals("read2")){
			String sql_1 = String.format("select id_2 from user_db where name = '%s'",userData.name);
			ResultSet res_1 = mOracle.query(sql_1);

			try {
				while (res_1.next()) {
					senario = res_1.getInt(1);
				}
				String sql = String.format("select * from scenario_db where id_l = '%d'",senario);
				ResultSet res = mOracle.query(sql);
				try {
					while (res.next()) {
						PageNo pageNo = new PageNo();
						pageNo.id = res.getInt(1);
						pageNo.scenario = res.getString(2);
						list.add(pageNo);
					}
					// JSON形式に変換
					String json = JSON.encode(list);
					// 出力
					out.println(json);
				} catch (SQLException e) {
					// TODO 自動生成された catch ブロック
					e.printStackTrace();
				}

			} catch (SQLException e) {
				// TODO 自動生成された catch ブロック
				e.printStackTrace();
			}
		} else if (userData.cmd.equals("read3")) {
			// DBからシナリオ読み込み
			// userData.senario = userData.senario + 1 ;
			//　シナリオ進む
			if(senario ==14){
				senario = userData.id;
			}else if(userData.id == 56){
				senario = userData.id;
			}else if(senario == 55 || senario == 64){
				System.out.println("終了");
			}else{
			senario = senario + 1;
			}

			String sql = String.format("select * from scenario_db where id_l ='%d'", senario);
			ResultSet res = mOracle.query(sql);
			try {
				while (res.next()) {
					PageNo pageNo = new PageNo();
					pageNo.id = res.getInt(1);
					pageNo.scenario = res.getString(2);
					list.add(pageNo);
				}
				// JSON形式に変換
				String json = JSON.encode(list);
				// 出力
				out.println(json);
			} catch (SQLException e) {
				// TODO 自動生成された catch ブロック
				e.printStackTrace();
			}

		} else if (userData.cmd.equals("read4")) {
			// DBからシナリオ読み込み
			//シナリオ戻る
			// userData.senario = userData.senario + 1 ;
			if(senario == 1 || senario ==16 || senario == 56){
				System.out.println("戻れないよぉ～");
			}else{

			senario = senario - 1;

			}

//			System.out.println("userData.senario＝" + senario);

			String sql = String.format("select * from scenario_db where id_l = '%d'", /* userData. */
					senario);
			ResultSet res = mOracle.query(sql);
			try {
				while (res.next()) {
					PageNo pageNo = new PageNo();
					pageNo.id = res.getInt(1);
					pageNo.scenario = res.getString(2);
					list.add(pageNo);
				}
				// JSON形式に変換
				String json = JSON.encode(list);
				// 出力
				out.println(json);
			} catch (SQLException e) {
				// TODO 自動生成された catch ブロック
				e.printStackTrace();
			}

		} else if(userData.cmd.equals("read5")) {
			String sql = String.format("update user_db set id_2 ='%d' where name = '%s'",senario,userData.name);
			mOracle.query(sql);
		}

	}
}
