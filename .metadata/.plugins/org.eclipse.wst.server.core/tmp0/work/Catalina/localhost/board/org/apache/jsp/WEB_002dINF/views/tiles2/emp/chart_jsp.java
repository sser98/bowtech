/*
 * Generated by the Jasper component of Apache Tomcat
 * Version: Apache Tomcat/8.5.59
 * Generated at: 2021-03-02 10:28:11 UTC
 * Note: The last modified time of this file was set to
 *       the last modified time of the source file after
 *       generation to assist with modification tracking.
 */
package org.apache.jsp.WEB_002dINF.views.tiles2.emp;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;

public final class chart_jsp extends org.apache.jasper.runtime.HttpJspBase
    implements org.apache.jasper.runtime.JspSourceDependent,
                 org.apache.jasper.runtime.JspSourceImports {

  private static final javax.servlet.jsp.JspFactory _jspxFactory =
          javax.servlet.jsp.JspFactory.getDefaultFactory();

  private static java.util.Map<java.lang.String,java.lang.Long> _jspx_dependants;

  private static final java.util.Set<java.lang.String> _jspx_imports_packages;

  private static final java.util.Set<java.lang.String> _jspx_imports_classes;

  static {
    _jspx_imports_packages = new java.util.HashSet<>();
    _jspx_imports_packages.add("javax.servlet");
    _jspx_imports_packages.add("javax.servlet.http");
    _jspx_imports_packages.add("javax.servlet.jsp");
    _jspx_imports_classes = null;
  }

  private volatile javax.el.ExpressionFactory _el_expressionfactory;
  private volatile org.apache.tomcat.InstanceManager _jsp_instancemanager;

  public java.util.Map<java.lang.String,java.lang.Long> getDependants() {
    return _jspx_dependants;
  }

  public java.util.Set<java.lang.String> getPackageImports() {
    return _jspx_imports_packages;
  }

  public java.util.Set<java.lang.String> getClassImports() {
    return _jspx_imports_classes;
  }

  public javax.el.ExpressionFactory _jsp_getExpressionFactory() {
    if (_el_expressionfactory == null) {
      synchronized (this) {
        if (_el_expressionfactory == null) {
          _el_expressionfactory = _jspxFactory.getJspApplicationContext(getServletConfig().getServletContext()).getExpressionFactory();
        }
      }
    }
    return _el_expressionfactory;
  }

  public org.apache.tomcat.InstanceManager _jsp_getInstanceManager() {
    if (_jsp_instancemanager == null) {
      synchronized (this) {
        if (_jsp_instancemanager == null) {
          _jsp_instancemanager = org.apache.jasper.runtime.InstanceManagerFactory.getInstanceManager(getServletConfig());
        }
      }
    }
    return _jsp_instancemanager;
  }

  public void _jspInit() {
  }

  public void _jspDestroy() {
  }

  public void _jspService(final javax.servlet.http.HttpServletRequest request, final javax.servlet.http.HttpServletResponse response)
      throws java.io.IOException, javax.servlet.ServletException {

    final java.lang.String _jspx_method = request.getMethod();
    if (!"GET".equals(_jspx_method) && !"POST".equals(_jspx_method) && !"HEAD".equals(_jspx_method) && !javax.servlet.DispatcherType.ERROR.equals(request.getDispatcherType())) {
      response.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED, "JSP들은 오직 GET, POST 또는 HEAD 메소드만을 허용합니다. Jasper는 OPTIONS 메소드 또한 허용합니다.");
      return;
    }

    final javax.servlet.jsp.PageContext pageContext;
    javax.servlet.http.HttpSession session = null;
    final javax.servlet.ServletContext application;
    final javax.servlet.ServletConfig config;
    javax.servlet.jsp.JspWriter out = null;
    final java.lang.Object page = this;
    javax.servlet.jsp.JspWriter _jspx_out = null;
    javax.servlet.jsp.PageContext _jspx_page_context = null;


    try {
      response.setContentType("text/html; charset=UTF-8");
      pageContext = _jspxFactory.getPageContext(this, request, response,
      			null, true, 8192, true);
      _jspx_page_context = pageContext;
      application = pageContext.getServletContext();
      config = pageContext.getServletConfig();
      session = pageContext.getSession();
      out = pageContext.getOut();
      _jspx_out = out;

      out.write("\r\n");
      out.write("\r\n");
      out.write("<script src=\"https://code.highcharts.com/highcharts.js\"></script>\r\n");
      out.write("\r\n");
      out.write("<script src=\"https://code.highcharts.com/modules/data.js\"></script>\r\n");
      out.write("<script src=\"https://code.highcharts.com/modules/drilldown.js\"></script>\r\n");
      out.write("\r\n");
      out.write("<script src=\"https://code.highcharts.com/modules/exporting.js\"></script>\r\n");
      out.write("<script src=\"https://code.highcharts.com/modules/export-data.js\"></script>\r\n");
      out.write("<script src=\"https://code.highcharts.com/modules/accessibility.js\"></script>\r\n");
      out.write("\r\n");
      out.write("<style>\r\n");
      out.write("\r\n");
      out.write("\ttable {width: 300px;}\r\n");
      out.write("\ttable, th, td {border: solid 1px gray;}\r\n");
      out.write("\tth {text-align: center;}\r\n");
      out.write("\n");
      out.write(".highcharts-figure, .highcharts-data-table table {\r\n");
      out.write("    min-width: 320px; \r\n");
      out.write("    max-width: 800px;\r\n");
      out.write("    margin: 1em auto;\r\n");
      out.write("}\r\n");
      out.write("\r\n");
      out.write(".highcharts-data-table table {\r\n");
      out.write("\tfont-family: Verdana, sans-serif;\r\n");
      out.write("\tborder-collapse: collapse;\r\n");
      out.write("\tborder: 1px solid #EBEBEB;\r\n");
      out.write("\tmargin: 10px auto;\r\n");
      out.write("\ttext-align: center;\r\n");
      out.write("\twidth: 100%;\r\n");
      out.write("\tmax-width: 500px;\r\n");
      out.write("}\r\n");
      out.write(".highcharts-data-table caption {\r\n");
      out.write("    padding: 1em 0;\r\n");
      out.write("    font-size: 1.2em;\r\n");
      out.write("    color: #555;\r\n");
      out.write("}\r\n");
      out.write(".highcharts-data-table th {\r\n");
      out.write("\tfont-weight: 600;\r\n");
      out.write("    padding: 0.5em;\r\n");
      out.write("}\r\n");
      out.write(".highcharts-data-table td, .highcharts-data-table th, .highcharts-data-table caption {\r\n");
      out.write("    padding: 0.5em;\r\n");
      out.write("}\r\n");
      out.write(".highcharts-data-table thead tr, .highcharts-data-table tr:nth-child(even) {\r\n");
      out.write("    background: #f8f8f8;\r\n");
      out.write("}\r\n");
      out.write(".highcharts-data-table tr:hover {\r\n");
      out.write("    background: #f1f7ff;\r\n");
      out.write("}\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("input[type=\"number\"] {\r\n");
      out.write("\tmin-width: 50px;\r\n");
      out.write("}\n");
      out.write("</style>\r\n");
      out.write("\r\n");
      out.write("<div align=\"center\">\r\n");
      out.write("\t<h2>우리회사 사원 통계정보(차트)</h2>\r\n");
      out.write("\t\r\n");
      out.write("\t<form name=\"searchFrm\" style=\"margin: 20px 0 50px 0; \">\r\n");
      out.write("\t\t<select name=\"searchType\" id=\"searchType\" style=\"height: 25px;\">\r\n");
      out.write("\t\t\t<option value=\"\">통계선택</option>\r\n");
      out.write("\t\t\t<option value=\"deptname\">부서별 인원통계</option>\r\n");
      out.write("\t\t\t<option value=\"gender\">성별 인원통계</option>\r\n");
      out.write("\t\t\t<option value=\"deptnameGender\">부서별 성별 인원통계</option>\r\n");
      out.write("\t\t</select>\r\n");
      out.write("\t</form>\r\n");
      out.write("\t\r\n");
      out.write("\t<div id=\"chart_container\" style=\"width: 80%;\"></div>\r\n");
      out.write("\t<div id=\"table_container\" style=\"width: 90%; margin: 20px 0;\"></div>\r\n");
      out.write("</div>\r\n");
      out.write("\r\n");
      out.write("<script type=\"text/javascript\">\n");
      out.write("\t$(document).ready(function(){\r\n");
      out.write("\t\t\r\n");
      out.write("\t\t$(\"select#searchType\").bind(\"change\", function(){\r\n");
      out.write("\t\t\tfunc_choice($(this).val()); \r\n");
      out.write("\t\t\t// $(this).val() 은 \"\" 또는 \"deptname\" 또는 \"gender\" 또는 \"deptnameGender\" 이다. \r\n");
      out.write("\t\t});\r\n");
      out.write("\t\t\r\n");
      out.write("\t});// end of $(document).ready()-------------------------\r\n");
      out.write("\t\r\n");
      out.write("\t\r\n");
      out.write("\tfunction func_choice(searchTypeVal) {\r\n");
      out.write("\t\t\r\n");
      out.write("\t\tswitch (searchTypeVal) {\r\n");
      out.write("\t\t\tcase \"\": // 통계선택을 선택한 경우\r\n");
      out.write("\t\t\t\t$(\"div#chart_container\").empty();\r\n");
      out.write("\t\t\t\t$(\"div#table_container\").empty();\r\n");
      out.write("\t\t\t\t$(\"div.highcharts-data-table\").empty();\r\n");
      out.write("\t\t\t\t\r\n");
      out.write("\t\t\t\tbreak;\r\n");
      out.write("\t\t\t\t\r\n");
      out.write("\t\t\tcase \"deptname\": // 부서별 인원통계를 선택한 경우 \r\n");
      out.write("\t\t\t\r\n");
      out.write("\t\t\t\t$.ajax({\r\n");
      out.write("\t\t\t\t\turl:\"");
      out.print( request.getContextPath());
      out.write("/chart/employeeCntByDeptname.action\",\r\n");
      out.write("\t\t\t\t\tdataType:\"JSON\",\r\n");
      out.write("\t\t\t\t\tsuccess:function(json){\r\n");
      out.write("\t\t\t\t\t\r\n");
      out.write("\t\t\t\t\t\t$(\"div#chart_container\").empty();\r\n");
      out.write("\t\t\t\t\t\t$(\"div#table_container\").empty();\r\n");
      out.write("\t\t\t\t\t\t$(\"div.highcharts-data-table\").empty();\r\n");
      out.write("\t\t\t\t\t\t\r\n");
      out.write("\t\t\t\t\t\tvar resultArr = [];\r\n");
      out.write("\t\t\t\t\t\t\r\n");
      out.write("\t\t\t\t\t\tfor(var i=0; i<json.length; i++) {\r\n");
      out.write("\t\t\t\t\t\t\tvar obj;\r\n");
      out.write("\t\t\t\t\t\t\t\r\n");
      out.write("\t\t\t\t\t\t\tif(json[i].department_name == \"Shipping\") {\r\n");
      out.write("\t\t\t\t\t\t\t\tobj = {name: json[i].department_name, \r\n");
      out.write("\t\t\t\t\t\t\t\t\t   y: Number(json[i].percentage),\r\n");
      out.write("\t\t\t\t\t\t\t\t\t   sliced: true,\r\n");
      out.write("\t\t\t\t\t\t\t           selected: true};\r\n");
      out.write("\t\t\t\t\t\t\t}\r\n");
      out.write("\t\t\t\t\t\t\telse {\r\n");
      out.write("\t\t\t\t\t\t\t\tobj = {name: json[i].department_name, \r\n");
      out.write("\t\t\t\t\t\t\t\t\t   y: Number(json[i].percentage)};\r\n");
      out.write("\t\t\t\t\t\t\t}\r\n");
      out.write("\t\t\t\t\t\t\t\r\n");
      out.write("\t\t\t\t\t\t\tresultArr.push(obj); // 배열속에 객체를 넣기 \r\n");
      out.write("\t\t\t\t\t\t}// end of for--------------------------\r\n");
      out.write("\t\t\t\t\t\t\r\n");
      out.write("\t\t\t\t\t\t////////////////////////////////////////////////////////\r\n");
      out.write("\t\t\t\t\t\tHighcharts.chart('chart_container', {\r\n");
      out.write("\t\t\t\t\t\t    chart: {\r\n");
      out.write("\t\t\t\t\t\t        plotBackgroundColor: null,\r\n");
      out.write("\t\t\t\t\t\t        plotBorderWidth: null,\r\n");
      out.write("\t\t\t\t\t\t        plotShadow: false,\r\n");
      out.write("\t\t\t\t\t\t        type: 'pie'\r\n");
      out.write("\t\t\t\t\t\t    },\r\n");
      out.write("\t\t\t\t\t\t    title: {\r\n");
      out.write("\t\t\t\t\t\t        text: '우리회사 부서별 인원통계'\r\n");
      out.write("\t\t\t\t\t\t    },\r\n");
      out.write("\t\t\t\t\t\t    tooltip: {\r\n");
      out.write("\t\t\t\t\t\t        pointFormat: '{series.name}: <b>{point.percentage:.1f}%</b>'\r\n");
      out.write("\t\t\t\t\t\t    },\r\n");
      out.write("\t\t\t\t\t\t    accessibility: {\r\n");
      out.write("\t\t\t\t\t\t        point: {\r\n");
      out.write("\t\t\t\t\t\t            valueSuffix: '%'\r\n");
      out.write("\t\t\t\t\t\t        }\r\n");
      out.write("\t\t\t\t\t\t    },\r\n");
      out.write("\t\t\t\t\t\t    plotOptions: {\r\n");
      out.write("\t\t\t\t\t\t        pie: {\r\n");
      out.write("\t\t\t\t\t\t            allowPointSelect: true,\r\n");
      out.write("\t\t\t\t\t\t            cursor: 'pointer',\r\n");
      out.write("\t\t\t\t\t\t            dataLabels: {\r\n");
      out.write("\t\t\t\t\t\t                enabled: true,\r\n");
      out.write("\t\t\t\t\t\t                format: '<b>{point.name}</b>: {point.percentage:.1f} %'\r\n");
      out.write("\t\t\t\t\t\t            }\r\n");
      out.write("\t\t\t\t\t\t        }\r\n");
      out.write("\t\t\t\t\t\t    },\r\n");
      out.write("\t\t\t\t\t\t    series: [{\r\n");
      out.write("\t\t\t\t\t\t        name: '인원비율',\r\n");
      out.write("\t\t\t\t\t\t        colorByPoint: true,\r\n");
      out.write("\t\t\t\t\t\t        data: resultArr\r\n");
      out.write("\t\t\t\t\t\t    }]\r\n");
      out.write("\t\t\t\t\t\t});\r\n");
      out.write("\t\t\t\t\t\t////////////////////////////////////////////////////////\t\r\n");
      out.write("\t\t\t\t\t\t\r\n");
      out.write("\t\t\t\t\t\tvar html = \"<table>\";\r\n");
      out.write("\t                    html += \"<tr>\" +\r\n");
      out.write("\t                                \"<th>부서명</th>\" +\r\n");
      out.write("\t                                \"<th>인원수</th>\" +\r\n");
      out.write("\t                                \"<th>퍼센티지</th>\" +\r\n");
      out.write("\t                            \"</tr>\";\r\n");
      out.write("\t\t\t\t\t\t\r\n");
      out.write("\t                    $.each(json, function(index, item){\r\n");
      out.write("\t                    \thtml += \"<tr>\" +\r\n");
      out.write("\t                    \t            \"<td style='text-align: center;'>\"+ item.department_name +\"</td>\" +\r\n");
      out.write("\t                    \t            \"<td style='text-align: center;'>\"+ item.cnt +\"</td>\" +\r\n");
      out.write("\t                    \t            \"<td style='text-align: center;'>\"+ Number(item.percentage) +\"</td>\" +\r\n");
      out.write("\t                    \t        \"</tr>\";\r\n");
      out.write("\t                    });        \r\n");
      out.write("\t                            \r\n");
      out.write("\t                    html += \"</table>\";\r\n");
      out.write("\t                    \r\n");
      out.write("\t                    $(\"div#table_container\").html(html);\r\n");
      out.write("\t\t\t\t\t},\r\n");
      out.write("\t\t\t\t\terror: function(request, status, error){\r\n");
      out.write("\t\t\t\t\t\talert(\"code: \"+request.status+\"\\n\"+\"message: \"+request.responseText+\"\\n\"+\"error: \"+error);\r\n");
      out.write("\t\t\t\t\t}\r\n");
      out.write("\t\t\t\t});\r\n");
      out.write("\t\t\t\r\n");
      out.write("\t\t\t\tbreak;\t\r\n");
      out.write("\t\r\n");
      out.write("\t\t\t\t\r\n");
      out.write("\t\t\tcase \"gender\": // 성별 인원통계를 선택한 경우 \r\n");
      out.write("\t\t\t\t\r\n");
      out.write("\t\t\t\t$.ajax({\r\n");
      out.write("\t\t\t\t\turl:\"");
      out.print( request.getContextPath());
      out.write("/chart/employeeCntByGender.action\",\r\n");
      out.write("\t\t\t\t\tdataType:\"JSON\",\r\n");
      out.write("\t\t\t\t\tsuccess:function(json){\r\n");
      out.write("\t\t\t\t\t\r\n");
      out.write("\t\t\t\t\t\t$(\"div#chart_container\").empty();\r\n");
      out.write("\t\t\t\t\t\t$(\"div#table_container\").empty();\r\n");
      out.write("\t\t\t\t\t\t$(\"div.highcharts-data-table\").empty();\r\n");
      out.write("\t\t\t\t\t\t\r\n");
      out.write("\t\t\t\t\t\tvar resultArr = [];\r\n");
      out.write("\t\t\t\t\t\t\r\n");
      out.write("\t\t\t\t\t\tfor(var i=0; i<json.length; i++) {\r\n");
      out.write("\t\t\t\t\t\t\tvar obj;\r\n");
      out.write("\t\t\t\t\t\t\t\r\n");
      out.write("\t\t\t\t\t\t\tif(json[i].gender == \"남\") {\r\n");
      out.write("\t\t\t\t\t\t\t\tobj = {name: json[i].gender,\r\n");
      out.write("\t\t\t\t\t\t\t\t\t   y: Number(json[i].percentage),\r\n");
      out.write("\t\t\t\t\t\t\t\t\t   sliced: true,\r\n");
      out.write("\t\t\t\t\t\t\t\t\t   selected: true};\r\n");
      out.write("\t\t\t\t\t\t\t}\r\n");
      out.write("\t\t\t\t\t\t\telse {\r\n");
      out.write("\t\t\t\t\t\t\t\tobj = {name: json[i].gender,\r\n");
      out.write("\t\t\t\t\t\t\t\t\t   y: Number(json[i].percentage)};\r\n");
      out.write("\t\t\t\t\t\t\t}\r\n");
      out.write("\t\t\t\t\t\t\t\r\n");
      out.write("\t\t\t\t\t\t\tresultArr.push(obj); // 배열속에 객체를 넣기\r\n");
      out.write("\t\t\t\t\t\t}// end of for--------------------------\r\n");
      out.write("\t\t\t\t\t\t\r\n");
      out.write("\t\t\t\t\t\t////////////////////////////////////////////////////////\r\n");
      out.write("\t\t\t\t\t\tHighcharts.chart('chart_container', {\r\n");
      out.write("\t\t\t\t\t\t    chart: {\r\n");
      out.write("\t\t\t\t\t\t        plotBackgroundColor: null,\r\n");
      out.write("\t\t\t\t\t\t        plotBorderWidth: null,\r\n");
      out.write("\t\t\t\t\t\t        plotShadow: false,\r\n");
      out.write("\t\t\t\t\t\t        type: 'pie'\r\n");
      out.write("\t\t\t\t\t\t    },\r\n");
      out.write("\t\t\t\t\t\t    title: {\r\n");
      out.write("\t\t\t\t\t\t        text: '우리회사 성별 인원통계'\r\n");
      out.write("\t\t\t\t\t\t    },\r\n");
      out.write("\t\t\t\t\t\t    tooltip: {\r\n");
      out.write("\t\t\t\t\t\t        pointFormat: '{series.name}: <b>{point.percentage:.1f}%</b>'\r\n");
      out.write("\t\t\t\t\t\t    },\r\n");
      out.write("\t\t\t\t\t\t    accessibility: {\r\n");
      out.write("\t\t\t\t\t\t        point: {\r\n");
      out.write("\t\t\t\t\t\t            valueSuffix: '%'\r\n");
      out.write("\t\t\t\t\t\t        }\r\n");
      out.write("\t\t\t\t\t\t    },\r\n");
      out.write("\t\t\t\t\t\t    plotOptions: {\r\n");
      out.write("\t\t\t\t\t\t        pie: {\r\n");
      out.write("\t\t\t\t\t\t            allowPointSelect: true,\r\n");
      out.write("\t\t\t\t\t\t            cursor: 'pointer',\r\n");
      out.write("\t\t\t\t\t\t            dataLabels: {\r\n");
      out.write("\t\t\t\t\t\t                enabled: false\r\n");
      out.write("\t\t\t\t\t\t            },\r\n");
      out.write("\t\t\t\t\t\t            showInLegend: true\r\n");
      out.write("\t\t\t\t\t\t        }\r\n");
      out.write("\t\t\t\t\t\t    },\r\n");
      out.write("\t\t\t\t\t\t    series: [{\r\n");
      out.write("\t\t\t\t\t\t        name: '인원비율',\r\n");
      out.write("\t\t\t\t\t\t        colorByPoint: true,\r\n");
      out.write("\t\t\t\t\t\t        data: resultArr\r\n");
      out.write("\t\t\t\t\t\t    }]\r\n");
      out.write("\t\t\t\t\t\t});\r\n");
      out.write("\t\t\t\t\t\t////////////////////////////////////////////////////////\t\r\n");
      out.write("\t\t\t\t\t\t\r\n");
      out.write("\t\t\t\t\t\tvar html = \"<table>\";\r\n");
      out.write("\t                    html += \"<tr>\" +\r\n");
      out.write("\t                                \"<th>성별</th>\" +\r\n");
      out.write("\t                                \"<th>인원수</th>\" +\r\n");
      out.write("\t                                \"<th>퍼센티지</th>\" +\r\n");
      out.write("\t                            \"</tr>\";\r\n");
      out.write("\t\t\t\t\t\t\r\n");
      out.write("\t                    $.each(json, function(index, item){\r\n");
      out.write("\t                    \thtml += \"<tr>\" +\r\n");
      out.write("\t                    \t            \"<td style='text-align: center;'>\"+ item.gender +\"</td>\" +\r\n");
      out.write("\t                    \t            \"<td style='text-align: center;'>\"+ item.cnt +\"</td>\" +\r\n");
      out.write("\t                    \t            \"<td style='text-align: center;'>\"+ Number(item.percentage) +\"</td>\" +\r\n");
      out.write("\t                    \t        \"</tr>\";\r\n");
      out.write("\t                    });        \r\n");
      out.write("\t                            \r\n");
      out.write("\t                    html += \"</table>\";\r\n");
      out.write("\t                    \r\n");
      out.write("\t                    $(\"div#table_container\").html(html);\r\n");
      out.write("\t\t\t\t\t},\r\n");
      out.write("\t\t\t\t\terror: function(request, status, error){\r\n");
      out.write("\t\t\t\t\t\talert(\"code: \"+request.status+\"\\n\"+\"message: \"+request.responseText+\"\\n\"+\"error: \"+error);\r\n");
      out.write("\t\t\t\t\t}\r\n");
      out.write("\t\t\t\t});\t\t\t\t\r\n");
      out.write("\t\t\t\t\r\n");
      out.write("\t\t\t\tbreak;\r\n");
      out.write("\t\t\t\t\r\n");
      out.write("\t\t\t\t\r\n");
      out.write("\t\t\tcase \"deptnameGender\": // 부서별 성별 인원통계를 선택한 경우 \r\n");
      out.write("\t\t\t\t$.ajax({\r\n");
      out.write("\t\t\t\t\turl:\"");
      out.print( request.getContextPath());
      out.write("/chart/employeeCntByDeptname.action\",\r\n");
      out.write("\t\t\t\t\tdataType:\"JSON\",\r\n");
      out.write("\t\t\t\t\tsuccess:function(json1) {\r\n");
      out.write("\t\t\t\t\t\t\r\n");
      out.write("\t\t\t\t\t\t$(\"div#chart_container\").empty();\r\n");
      out.write("\t\t\t\t\t\t$(\"div#table_container\").empty();\r\n");
      out.write("\t\t\t\t\t\t$(\"div.highcharts-data-table\").empty();\r\n");
      out.write("\t\t\t\t\t\t\r\n");
      out.write("\t\t\t\t\t\tvar deptnameArr = []; // 부서명별 인원수 퍼센티지 객체 배열 \r\n");
      out.write("\t\t\t\t\t\t\r\n");
      out.write("\t\t\t\t\t\t$.each(json1, function(index, item){\r\n");
      out.write("\t\t\t\t\t\t\tdeptnameArr.push({\"name\": item.department_name,\r\n");
      out.write("\t\t\t                                  \"y\": Number(item.percentage),\r\n");
      out.write("\t\t\t                                  \"drilldown\": item.department_name,\r\n");
      out.write("\t\t\t                                  \"cnt\": item.cnt});\r\n");
      out.write("\t\t\t\t\t\t});// end of $.each(json1, function(index, item){}-----------\r\n");
      out.write("\t\t\t\t\t\t\t\t\r\n");
      out.write("\t\t\t\t\t\tvar genderArr = []; // 특정 부서명에 근무하는 직원들의 성별 인원수 퍼센티지 객체 배열 \r\n");
      out.write("\t\t\t\t\t\t\r\n");
      out.write("\t\t\t\t\t\t$.each(json1, function(index1, item1){\r\n");
      out.write("\t\t\t\t\t\t\t\r\n");
      out.write("\t\t\t\t\t\t\t$.ajax({\r\n");
      out.write("\t\t\t\t\t\t\t\turl:\"");
      out.print( request.getContextPath());
      out.write("/chart/genderCntSpecialDeptname.action\",\r\n");
      out.write("\t\t\t\t\t\t\t\ttype:\"GET\",\r\n");
      out.write("\t\t\t\t\t\t\t\tdata:{\"deptname\":item1.department_name},\r\n");
      out.write("\t\t\t\t\t\t\t\tdataType:\"JSON\",\r\n");
      out.write("\t\t\t\t\t\t\t\tsuccess:function(json2) {\r\n");
      out.write("\t\t\t\t\t\t\t\t\t\r\n");
      out.write("\t\t\t\t\t\t\t\t\tvar subArr = [];\r\n");
      out.write("\t\t\t\t\t\t\t\t\t\r\n");
      out.write("\t\t\t\t\t\t\t\t\t$.each(json2, function(index2, item2){\r\n");
      out.write("\t\t\t\t\t\t\t\t\t\t\r\n");
      out.write("\t\t\t\t\t\t\t\t\t\tsubArr.push([item2.gender, \r\n");
      out.write("\t\t\t\t\t\t\t\t\t\t\t         Number(item2.percentage)]);\r\n");
      out.write("\t\t\t\t\t\t\t\t\t\t\r\n");
      out.write("\t\t\t\t\t\t\t\t\t});// end of $.each(json2, function(index2, item2){})-----------\r\n");
      out.write("\t\t\t\t\t\t\t\t\t\r\n");
      out.write("\t\t\t\t\t\t\t\t\tgenderArr.push({\"name\": item1.department_name,\r\n");
      out.write("\t\t\t\t\t\t\t\t                    \"id\": item1.department_name,\r\n");
      out.write("\t\t\t\t\t\t\t\t                    \"data\": subArr});\r\n");
      out.write("\t\t\t\t\t\t\t\t\t\r\n");
      out.write("\t\t\t\t\t\t\t\t},\r\n");
      out.write("\t\t\t\t\t\t\t\terror: function(request, status, error){\r\n");
      out.write("\t\t\t\t\t\t\t\t\talert(\"code: \"+request.status+\"\\n\"+\"message: \"+request.responseText+\"\\n\"+\"error: \"+error);\r\n");
      out.write("\t\t\t\t\t\t\t\t}\r\n");
      out.write("\t\t\t\t\t\t\t});\r\n");
      out.write("\t\t\t\t\t\t\t\r\n");
      out.write("\t\t\t\t\t\t});// end of $.each(json1, function(index1, item1){})-----------\r\n");
      out.write("\t\t\t\t\t\t\r\n");
      out.write("\t\t\t\t\t   ////////////////////////////////////////////////////////\r\n");
      out.write("\t\t\t\t\t\tHighcharts.chart('chart_container', {\r\n");
      out.write("\t\t\t\t\t\t    chart: {\r\n");
      out.write("\t\t\t\t\t\t        type: 'column'\r\n");
      out.write("\t\t\t\t\t\t    },\r\n");
      out.write("\t\t\t\t\t\t    title: {\r\n");
      out.write("\t\t\t\t\t\t        text: '부서별 남녀 비율'\r\n");
      out.write("\t\t\t\t\t\t    },\r\n");
      out.write("\t\t\t\t\t\t    subtitle: {\r\n");
      out.write("\t\t\t\t\t\t     ");
      out.write("     \r\n");
      out.write("\t\t\t\t\t\t    },\r\n");
      out.write("\t\t\t\t\t\t    accessibility: {\r\n");
      out.write("\t\t\t\t\t\t        announceNewData: {\r\n");
      out.write("\t\t\t\t\t\t            enabled: true\r\n");
      out.write("\t\t\t\t\t\t        }\r\n");
      out.write("\t\t\t\t\t\t    },\r\n");
      out.write("\t\t\t\t\t\t    xAxis: {\r\n");
      out.write("\t\t\t\t\t\t        type: 'category'\r\n");
      out.write("\t\t\t\t\t\t    },\r\n");
      out.write("\t\t\t\t\t\t    yAxis: {\r\n");
      out.write("\t\t\t\t\t\t        title: {\r\n");
      out.write("\t\t\t\t\t\t            text: '구성비율(%)'\r\n");
      out.write("\t\t\t\t\t\t        }\r\n");
      out.write("\t\t\t\t\t\t\r\n");
      out.write("\t\t\t\t\t\t    },\r\n");
      out.write("\t\t\t\t\t\t    legend: {\r\n");
      out.write("\t\t\t\t\t\t        enabled: false\r\n");
      out.write("\t\t\t\t\t\t    },\r\n");
      out.write("\t\t\t\t\t\t    plotOptions: {\r\n");
      out.write("\t\t\t\t\t\t        series: {\r\n");
      out.write("\t\t\t\t\t\t            borderWidth: 0,\r\n");
      out.write("\t\t\t\t\t\t            dataLabels: {\r\n");
      out.write("\t\t\t\t\t\t                enabled: true,\r\n");
      out.write("\t\t\t\t\t\t                format: '{point.y:.1f}%'\r\n");
      out.write("\t\t\t\t\t\t            }\r\n");
      out.write("\t\t\t\t\t\t        }\r\n");
      out.write("\t\t\t\t\t\t    },\r\n");
      out.write("\t\t\t\t\t\t\r\n");
      out.write("\t\t\t\t\t\t    tooltip: {\r\n");
      out.write("\t\t\t\t\t\t        headerFormat: '<span style=\"font-size:11px\">{series.name}</span><br>',\r\n");
      out.write("\t\t\t\t\t\t        pointFormat: '<span style=\"color:{point.color}\">{point.name}</span>: <b>{point.y:.2f}%</b> of total, {point.cnt}명<br/>'\r\n");
      out.write("\t\t\t\t\t\t    },\r\n");
      out.write("\t\t\t\t\t\t\r\n");
      out.write("\t\t\t\t\t\t    series: [{\r\n");
      out.write("\t\t\t\t\t\t              name: \"부서명\",\r\n");
      out.write("\t\t\t\t\t\t              colorByPoint: true,\r\n");
      out.write("\t\t\t\t\t\t              data: deptnameArr    // **** 위에서 구한 값을 대입시킴. 부서명별 인원수 퍼센티지 객체 배열  ****\r\n");
      out.write("\t\t\t\t\t\t             }],\r\n");
      out.write("\t\t\t\t\t\t    drilldown: {\r\n");
      out.write("\t\t\t\t\t\t                 series: genderArr // **** 위에서 구한 값을 대입시킴. 특정 부서명에 근무하는 직원들의 성별 인원수 퍼센티지 객체 배열  ****\r\n");
      out.write("\t\t\t\t\t\t               }\r\n");
      out.write("\t\t\t\t\t\t });  \r\n");
      out.write("\t\t\t\t\t\t////////////////////////////////////////////////////////\r\n");
      out.write("\t\t\t\t\t},\r\n");
      out.write("\t\t\t\t\terror: function(request, status, error){\r\n");
      out.write("\t\t\t\t\t\talert(\"code: \"+request.status+\"\\n\"+\"message: \"+request.responseText+\"\\n\"+\"error: \"+error);\r\n");
      out.write("\t\t\t\t\t}\r\n");
      out.write("\t\t\t\t});\r\n");
      out.write("\t\t\t\t\r\n");
      out.write("\t\t\t\tbreak;\t\t\t\t\r\n");
      out.write("\t\t}\r\n");
      out.write("\t\t\t\r\n");
      out.write("\t}// end of function func_choice(searchTypeVal) {}----------------\r\n");
      out.write("\n");
      out.write("</script>\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("    ");
    } catch (java.lang.Throwable t) {
      if (!(t instanceof javax.servlet.jsp.SkipPageException)){
        out = _jspx_out;
        if (out != null && out.getBufferSize() != 0)
          try {
            if (response.isCommitted()) {
              out.flush();
            } else {
              out.clearBuffer();
            }
          } catch (java.io.IOException e) {}
        if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
        else throw new ServletException(t);
      }
    } finally {
      _jspxFactory.releasePageContext(_jspx_page_context);
    }
  }
}
