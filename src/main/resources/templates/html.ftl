<!DOCTYPE html>
<html lang="en">

<head>
  <meta charset="utf-8"/>
  <meta http-equiv="X-UA-Compatible" content="IE=edge"/>
  <meta name="viewport" content="width=device-width,initial-scale=1.0"/>
  <title>${title}</title>
  <style>
    * {
      padding: 0;
      margin: 0;
      list-style: none;
    }

    body {
      font-family: "Source Han Sans CN", "PingFang SC", "Helvetica", "Arial",
      "黑体", "宋体", sans-serif;
      line-height: 1.5;
      color: #333;
    }

    .main {
      max-width: 1000px;
      width: 100%;
      background: #fff;
      margin: 0 auto;
      padding-bottom: 40px;
    }

    .main .top {
      width: 100%;
    }

    .main .top .cover {
      width: 100%;
      height: 1754px;

    <#if background?? && background != ""> background-image: url("${background}");
    </#if> background-repeat: no-repeat;
    }

    .main .top img {
      width: 100%;
    }

    .main .top .title {
      text-align: left;
      font-weight: 600;
      color: #fff;
      letter-spacing: 3px;
      padding-left: 30px;
      padding-top: 45%;
    }

    .main .top .title p {
      font-size: 38px;
      line-height: 70px;
      font-weight: 600;
    }

    .main .top .info {
      padding-left: 30px;
      margin-top: 30px;
      color: #fff;
    }

    .main .top .info .item {
      margin-top: 30px;
    }

    .main .top .info .label {
      font-size: 14px;
      font-weight: 400;
    }

    .main .top .info .value {
      font-size: 20px;
      font-weight: 500;
    }

    .main .top .catalogue {

    }

    @media print {
      .main .top .catalogue {
        height: 3560px;
      }
    }

    .main .top .catalogue .item {
      width: 80%;
      margin: 20px auto 0 auto;
      text-align: left;
      line-height: 40px;
    }

    .main .top .catalogue .item p {
      font-size: 24px;
      color: #333;
      font-weight: 600;
    }

    .main .top .catalogue .page-symbol {
      font-size: 24px;
      color: #333;
      font-weight: 600;
      white-space: nowrap;
      width: 740px;
      overflow-x: hidden;
      display: inline-block;
    }

    .main .top .catalogue .page-symbol label {
      font-size: 18px;
      color: #333;
    }

    .main .top .catalogue .page-num {
      font-size: 20px;
      padding-left: 10px;
      float: right;
      color: #333;
      font-weight: 600;
    }

    .main .top .catalogue .item li {
      font-size: 18px;
      color: #333;
    }

    .box-content {
      padding: 0 24px;
    }

    .box-content .temp-box {
      margin-top: 40px;
      margin-bottom: 40px;
      overflow: hidden;
    }

    @media print {
      .box-content .temp-box {
        margin-top: 0;
      }
    }

    .box-content .temp-box .title {
      font-size: 24px;
      line-height: 26px;
      font-weight: 500;
      color: #333333;
      margin-bottom: 16px;
    }

    .box-content .temp-box .title:before {
      content: "";
      display: inline-block;
      width: 4px;
      height: 18px;
      color: #0074e3;
      background: #0074e3;
      margin-right: 8px;
      vertical-align: middle;
      margin-top: -4px;
    }

    .box-content .temp-box .content {
      padding: 0 12px;
      margin-bottom: 24px;
    }

    .box-content .temp-box .content .small-title {
      font-size: 16px;
      color: #333333;
      line-height: 24px;
      font-weight: 500;
      margin-bottom: 4px;
    }

    .box-content .temp-box .content .desc {
      font-size: 14px;
      color: #657085;
      line-height: 22px;
      margin-bottom: 16px;
    }

    .box-content .temp-box .content .table {
      width: 100%;
      border-collapse: collapse;
      table-layout: auto;
      border: 1px solid #ebeff2;
      font-size: 14px;
    }

    .box-content .temp-box .content .table thead tr {
      border-bottom: 1px solid #ebeff2;
      height: 40px;
    }

    .box-content .temp-box .content .table thead tr th {
      background-color: #f8fafc;
      padding: 0 24px;
      border-right: 1px solid #ebeff2;
      color: #657085;
      font-weight: 400;
      box-sizing: border-box;
    }

    .box-content .temp-box .content .table tbody tr {
      border-bottom: 1px solid #ebeff2;
      height: 40px;
    }

    .box-content .temp-box .content .table tbody tr td {
      padding: 0 24px;
      border-right: 1px solid #ebeff2;
      display: table-cell;
      color: #333;
      box-sizing: border-box;
      text-align: center;
    }

    .box-content .temp-box .content .table-box {
      line-height: 40px;
      font-size: 14px;
      overflow: hidden;
      text-align: center;
      border-left: 1px solid #ebeff2;
    }

    .box-content .temp-box .content .table-box li {
      float: left;
      width: 33.3%;
      box-sizing: border-box;
      border: 1px solid #ebeff2;
      border-left: 0;
    }

    .box-content .temp-box .content .table-box li > p {
      width: 60%;
      float: left;
      box-sizing: border-box;
      border-right: 1px solid #ebeff2;
      background-color: #f8fafc;
      color: #657085;
    }

    .box-content .temp-box .content .table-box li > span {
      width: 40%;
      float: left;
      color: #333;
    }

    .box-content .temp-box .content .total-box {
      width: 100%;
      border: 1px solid #ebeff2;
      padding: 24px 0;
      text-align: center;
      font-size: 12px;
      color: #657085;
    }

    .box-content .temp-box .content .total-box p {
      font-size: 16px;
      font-weight: 500;
      margin-bottom: 4px;
    }

    .box-content .temp-box .content .total-box p span {
      color: #0074e3;
    }

    .echarts-box {
      width: 100%;
      text-align: center;
    }

    .echarts-box img {
      max-width: 100%;
      max-height: 100%;
    }

    .text {
      font-size: 14px;
      color: #333;
      line-height: 28px;
      margin-top: 10px;
    }
  </style>
</head>

<body>
<div class="main">
  <div class="top">
    <div class="cover">
        <#--        <#if background?? && background != "">-->
        <#--            <img src="${background}"/>-->
        <#--        </#if>-->
      <div class="title">
          <#--            <#if titleDesc?? && titleDesc != "">-->
          <#--                <span>${titleDesc}</span>-->
          <#--            </#if>-->
          <#if title?? && title != "">
            <p>${title}</p>
          </#if>
      </div>
      <div class="info">
          <#list info?keys as key>
            <div class="item">
              <div class="label">
                  ${key}
              </div>
              <div class="value">
                  ${info[key]}
              </div>
            </div>
          </#list>
      </div>
    </div>
    <div class="catalogue">
        <#if infoImage?? && infoImage != "">
          <img src="${infoImage}"/>
        </#if>
        <#list catalogue?keys as key>
          <div class="item">
            <p>
              <label class="page-symbol">${key}
                ........................................................................................................................</label>
              <label class="page-num">${catalogue[key].num}</label>
            </p>
            <ul>
                <#list catalogue[key].childs as value>
                  <p class="page-symbol">
                    <label>${value.name}</label>
                    ........................................................................................................................
                  </p>
                  <label class="page-num">${value.num}</label>
                </#list>
            </ul>
          </div>
        </#list>
    </div>
  </div>
  <div class="box-content">
    <div class="temp-box">
        <#list content?keys as key>
          <div class="title">${key}</div>
            <#assign item = content[key]>
            <#list item as value>
              <div class="content">
                  <#if value.index?? && value.index != "">
                    <div class="small-title">${value.index}</div>
                  </#if>
                  <#if value.description?? && value.description != "">
                    <div class="desc">${value.description}</div>
                  </#if>
                  <#if value.stat?? && (value.stat?size > 1)>
                    <ul class="table-box">
                        <#list value.stat?keys as key>
                          <li>
                            <p>${key}</p><span>${value.stat[key]}</span>
                          </li>
                        </#list>
                    </ul>
                  </#if>
                  <#if value.stat??&&(value.stat?size = 1)>
                    <ul class="total-box">
                        <#list value.stat?keys as key>
                          <li>
                            <p>${key}<span>${value.stat[key]}</span></p>
                              <#if value.statDescription?? && value.statDescription != "">
                                  ${value.statDescription}
                              </#if>
                          </li>
                        </#list>
                    </ul>
                  </#if>
                  <#if value.base64?? && value.base64 != "">
                    <div class="echarts-box">
                      <img src="${value.base64}"/>
                    </div>
                  </#if>
                  <#if value.texts?? && (value.texts?size > 0)>
                    <div class="text">
                        <#list value.texts as t>
                          <p>${t}</p>
                        </#list>
                    </div>
                  </#if>
                  <#if value.tablesTitle?? && (value.tablesTitle?size > 0)>
                    <table class="table">
                      <tbody>
                      <tr>
                          <#list value.tablesTitle as tt>
                            <th>${tt}</th>
                          </#list>
                      </tr>
                      </tbody>
                      <tbody>
                      <#list value.tablesValue as t1>
                        <tr>
                            <#list t1 as tv>
                              <td>${tv}</td>
                            </#list>
                        </tr>
                      </#list>
                      </tbody>
                    </table>
                  </#if>
              </div>
            </#list>
        </#list>
    </div>
  </div>
</div>
</body>

</html>