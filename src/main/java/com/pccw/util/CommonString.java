package com.pccw.util;

public class CommonString {

    public static final String indexHtml="<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01//EN\" \"http://www.w3.org/TR/html4/strict.dtd\">\n" +
            "<HTML>\n" +
            "<HEAD>\n" +
            "  <TITLE> PCCW Solutions - Delta Finder Between Base Version and Revision Version </TITLE>\n" +
            "  <meta http-equiv=\"content-type\" content=\"text/html; charset=UTF-8\">\n" +
            "  <link rel=\"stylesheet\" href=\"./css/zTreeStyle/zTreeStyle.css\" type=\"text/css\">\n" +
            "  <style>\n" +
            "    body {\n" +
            "      background-color: white;\n" +
            "      margin: 0;\n" +
            "      padding: 0;\n" +
            "      text-align: center;\n" +
            "    }\n" +
            "\n" +
            "    div, p, table, th, td {\n" +
            "      list-style: none;\n" +
            "      margin: 0;\n" +
            "      padding: 0;\n" +
            "      color: #333;\n" +
            "      font-size: 12px;\n" +
            "      font-family: dotum, Verdana, Arial, Helvetica, AppleGothic, sans-serif;\n" +
            "    }\n" +
            "\n" +
            "    #testIframe {\n" +
            "      margin-left: 10px;\n" +
            "    }\n" +
            "  </style>\n" +
            "  <script type=\"text/javascript\" src=\"./js/jquery-1.4.4.min.js\"></script>\n" +
            "  <script type=\"text/javascript\" src=\"./js/jquery.ztree.core.js\"></script>\n" +
            "  <SCRIPT type=\"text/javascript\">\n" +
            "    <!--\n" +
            "    var zTree;\n" +
            "    var demoIframe;\n" +
            "\n" +
            "    var setting = {\n" +
            "      view: {\n" +
            "        dblClickExpand: false,\n" +
            "        showLine: true,\n" +
            "        selectedMulti: false\n" +
            "      },\n" +
            "      data: {\n" +
            "        simpleData: {\n" +
            "          enable: true,\n" +
            "          idKey: \"id\",\n" +
            "          pIdKey: \"pId\",\n" +
            "          rootPId: \"\"\n" +
            "        }\n" +
            "      },\n" +
            "      callback: {\n" +
            "        beforeClick: function (treeId, treeNode) {\n" +
            "          var zTree = $.fn.zTree.getZTreeObj(\"tree\");\n" +
            "          if (treeNode.isParent) {\n" +
            "            zTree.expandNode(treeNode);\n" +
            "            return false;\n" +
            "          } else {\n" +
            "            demoIframe.attr(\"src\", treeNode.file + \".html\");\n" +
            "            return true;\n" +
            "          }\n" +
            "        }\n" +
            "      }\n" +
            "    };\n" +
            "\n" +
            "    var zNodes = [\n" +
            "      $FILE_NODE$\n" +
            "    ];\n" +
            "\n" +
            "    $(document).ready(function () {\n" +
            "      var t = $(\"#tree\");\n" +
            "      t = $.fn.zTree.init(t, setting, zNodes);\n" +
            "      demoIframe = $(\"#testIframe\");\n" +
            "      demoIframe.bind(\"load\", loadReady);\n" +
            "      var zTree = $.fn.zTree.getZTreeObj(\"tree\");\n" +
            "      zTree.selectNode(zTree.getNodeByParam(\"id\", 101));\n" +
            "\n" +
            "    });\n" +
            "\n" +
            "    function loadReady() {\n" +
            "      var bodyH = demoIframe.contents().find(\"body\").get(0).scrollHeight,\n" +
            "        htmlH = demoIframe.contents().find(\"html\").get(0).scrollHeight,\n" +
            "        maxH = Math.max(bodyH, htmlH), minH = Math.min(bodyH, htmlH),\n" +
            "        h = demoIframe.height() >= maxH ? minH : maxH;\n" +
            "      if (h < 530) h = 530;\n" +
            "      demoIframe.height(h);\n" +
            "    }\n" +
            "\n" +
            "    //-->\n" +
            "  </SCRIPT>\n" +
            "</HEAD>\n" +
            "\n" +
            "<BODY>\n" +
            "<TABLE border=0 height=600px align=left>\n" +
            "  <TR>\n" +
            "    <TD width=260px align=left valign=top style=\"BORDER-RIGHT: #999999 1px dashed\">\n" +
            "      <ul id=\"tree\" class=\"ztree\" style=\"width:260px; overflow:auto;\"></ul>\n" +
            "    </TD>\n" +
            "    <TD width=100% align=left valign=top>\n" +
            "      <IFRAME ID=\"testIframe\" Name=\"testIframe\" FRAMEBORDER=0 SCROLLING=AUTO width=100% height=600px\n" +
            "              SRC=\"main/index.html\"></IFRAME>\n" +
            "    </TD>\n" +
            "  </TR>\n" +
            "</TABLE>\n" +
            "\n" +
            "</BODY>\n" +
            "</HTML>\n";

    public static final String dataHtml="<!DOCTYPE html>\n" +
            "<html>\n" +
            "<head>\n" +
            "<link rel=\"stylesheet\" type=\"text/css\" href=\"../css/diffview.css\"/>\n" +
            "<link rel=\"stylesheet\" href=\"../css/demo.css\" type=\"text/css\">\n" +
            " <script type=\"text/javascript\" src=\"../js/jquery-1.4.4.min.js\"></script>\n" +
            "<script type=\"text/javascript\" src=\"../js/diffview.js\"></script>\n" +
            "<script type=\"text/javascript\" src=\"../js/difflib.js\"></script>"+
            "<script>\n" +
            "$(document).ready(function(){\n" +
            "const baseTextName=\"$$REVISION_1$$\";\n" +
            "const newTextName=\"$$REVISION_2$$\";\n" +
            "var file1=`$$FILE_1_CONTENT$$`;\n" +
            "var file2=`$$FILE_2_CONTENT$$`;\n" +
            "var viewType=0;\n" +
            "file1=difflib.stringAsLines(file1);\n" +
            "file2=difflib.stringAsLines(file2);"+
            "\n" +
            "\tvar sm = new difflib.SequenceMatcher(file1, file2);\n" +
            "opcodes = sm.get_opcodes();\n" +
            "divView=document.getElementById('div1');\n" +
            "divView.innerHTML = \"\";\n" +
            "divView.appendChild(diffview.buildView({\n" +
            "\t\tbaseTextLines: file1,\n" +
            "\t\tnewTextLines: file2,\n" +
            "\t\topcodes: opcodes,\n" +
            "\t\tbaseTextName: baseTextName,\n" +
            "\t\tnewTextName: newTextName,\t\t\t\t\n" +
            "\t\tviewType: viewType\n" +
            "\t}));\n" +
            "\t\n" +
            "\n" +
            "\n" +
            " \n" +
            "});"+
            "</script>\n" +
            "</head>\n" +
            "<body>\n" +
            "<h3>[ File Path: $$FILE_PATH$$ ]</h3>\n" +
            "<div>\n" +
            "<div>\n" +
            "\t\t<ul class=\"info\">\n" +
            "\t\t\t<li class=\"title\"><h2>Production Revision No:</h2>$$REVISION_1$$</li>\n" +
            "\t\t\t<li class=\"title\"><h2>SWD Revision No:</h2>$$REVISION_2$$</li>\t\t\n" +
            "\t\t</ul>\n" +
            "\t</div>\n" +
            "\n" +
            "<div id=\"div1\"></div>\n" +
            "</div>\n" +
            "</body>\n" +
            "</html>\n";

    public final static String url = "jdbc:h2:file:./h2/test";
    public final static String user = "sa";
    public final static String passwd = "s$cret";
    public final static String INDEX_LAYOUT2=" <!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01//EN\" \"http://www.w3.org/TR/html4/strict.dtd\">\n" +
            "<HTML>\n" +
            "<HEAD>\n" +
            "  <TITLE> PCCW Solutions - Delta Finder Between Base Version and Revision Version </TITLE>\n" +
            "  <meta http-equiv=\"content-type\" content=\"text/html; charset=UTF-8\">\n" +
            "  <link rel=\"stylesheet\" href=\"./css/zTreeStyle/zTreeStyle.css\" type=\"text/css\">\n" +
            "  </head>\n" +
            "  <body> \n" +
           "$IFRAMES_LIST$"+
            " \n</body>\n" +
            " </html>";

    public final static String IFRAME_HTML="<iframe src=\"$SRC_PATH$\" height=\"100%\" width=\"100%\" style='border:none;min-height:1000px;max-height:3000px;'></iframe> <p></p></br>";

    public final static String introPage="<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01//EN\" \"http://www.w3.org/TR/html4/strict.dtd\">\n" +
            "<html>\n" +
            "<Body>\n" +
            "<h3> Introduction </h3>\n" +
            "<p>This documentation provides information about the source code difference betweeen currrent production version with revised version </p>\n" +
            "<h3>Files Processed </h3>\n" +
            "<p>Total No of Files in this Documentation : <strong>$$FILE_COUNT$$</strong>\n" +
            "</Body>\n" +
            "</html>";
}
