<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="utf-8"/>
    <meta http-equiv="X-UA-Compatible" content="IE=edge"/>
    <meta name="viewport" content="width=device-width,initial-scale=1.0"/>
    <title>综合报表模板</title>
    <style>
        * {
            padding: 0;
            margin: 0;
            list-style: none;
        }

        body {
            font-family: "Microsoft YaHei", "PingFang SC", "Helvetica", "Arial", "黑体", "宋体", sans-serif;
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

        .main .top img {
            width: 100%;
        }

        .main .top .title {
            text-align: center;
            font-weight: 600;
            color: #2E2E2E;
            letter-spacing: 3px;
            margin-top: 30px;
        }

        .main .top .title span {
            font-size: 12px;
            line-height: 24px;
        }

        .main .top .title p {
            font-size: 50px;
            line-height: 70px;
        }

        .main .top .info {
            width: 422px;
            line-height: 48px;
            border: 1px solid #B3B3B3;
            border-bottom: 0;
            font-size: 12px;
            color: #333;
            margin: 60px auto;
        }

        .main .top .info .item {
            overflow: hidden;
            border-bottom: 1px solid #B3B3B3;
            text-align: center;
        }

        .main .top .info .item .label {
            float: left;
            width: 140px;
            box-sizing: border-box;
            border-right: 1px solid #B3B3B3;
        }

        .main .top .info .item .value {
            float: left;
            width: 280px;
        }

        .main .top .catalogue {
            margin-top: 300px;
        }

        .main .top .catalogue .item {
            width: 80%;
            margin: 40px auto 0 auto;
            text-align: center;
            line-height: 40px;
        }

        .main .top .catalogue .item p {
            font-size: 24px;
            color: #333;
            font-weight: 600;
        }

        .main .top .catalogue .item li {
            font-size: 18px;
            color: #333;
        }

        .box-content {
            margin-top: 120px;
            padding: 24px;
        }

        .box-content .temp-box {
            margin-bottom: 40px;
            overflow: hidden;
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
            color: #0074E3;
            background: #0074E3;
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
            border-top: 1px solid #ebeff2;
            border-left: 1px solid #ebeff2;
        }

        .box-content .temp-box .content .table-box li {
            float: left;
            width: 33.3%;
            box-sizing: border-box;
            border: 1px solid #ebeff2;
            border-top: 0;
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
            color: #0074E3;
        }

        .echarts-box {
            width: 100%;
            max-height: 300px;
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
        <img src="img_background.png"/>
        <div class="title">
            <span>Comprehensive Securit Report</span>
            <p>综合安全报表</p>
        </div>
        <div class="info">
            <div class="item">
                <div class="label">
                    报告时间范围
                </div>
                <div class="value">
                    2021-07-01至2021-09-30
                </div>
            </div>
            <div class="item">
                <div class="label">
                    报告生成时间
                </div>
                <div class="value">
                    2021-10-01 08:00:00
                </div>
            </div>
        </div>
        <div class="catalogue">
            <img src="img_directory.png"/>
            <div class="item">
                <p>第1章 整体摘要</p>
                <ul>
                    <li>1.1 安全概览</li>
                    <li>1.2 平台状态</li>
                </ul>
            </div>
            <div class="item">
                <p>第2章 事件分析</p>
                <ul>
                    <li>2.1 告警类型占比</li>
                    <li>2.2 威胁级别占比</li>
                    <li>2.3 告警趋势</li>
                    <li>2.4 web攻击数据统计</li>
                    <li>2.5 隧道或可疑代理数量统计</li>
                    <li>2.6 可疑访问数量统计</li>
                </ul>
            </div>
            <div class="item">
                <p>第3章 攻击者分析</p>
                <ul>
                    <li>3.1 攻击源TOP10</li>
                    <li>3.2 全球攻击源TOP5</li>
                </ul>
            </div>
            <div class="item">
                <p>第4章 受害资产分析</p>
                <ul>
                    <li>4.1 受害IP TOP10</li>
                    <li>4.2 受害URL TOP5</li>
                    <li>4.3 受害域名TOP5</li>
                </ul>
            </div>
            <div class="item">
                <p>第5章 网络协议分析</p>
                <ul>
                    <li>5.1 网络日志趋势</li>
                    <li>5.2 协议类型占比</li>
                    <li>5.3 网络会话数量统计</li>
                </ul>
            </div>
            <div class="item">
                <p>第6章 系统运维</p>
                <ul>
                    <li>6.1 网卡实时信息</li>
                </ul>
            </div>
            <div class="item">
                <p>第7章 安全防护建议</p>
                <ul>
                    <li>7.1 安全防护建议</li>
                </ul>
            </div>
        </div>
    </div>
    <div class="box-content">
        <div class="temp-box">
            <div class="title">第N章 我是标题</div>

            <div class="content">
                <div class="small-title">1.1 总计（多）</div>
                <div class="desc">我是组件描述</div>
                <ul class="table-box">
                    <li>
                        <p>总告警</p><span>1020</span>
                    </li>
                    <li>
                        <p>攻击成功</p><span>102</span>
                    </li>
                    <li>
                        <p>外部攻击</p><span>820</span>
                    </li>
                    <li>
                        <p>外联攻击</p><span>102</span>
                    </li>
                    <li>
                        <p>横向攻击</p><span>98</span>
                    </li>
                </ul>
            </div>

            <div class="content">
                <div class="small-title">1.2 总计</div>
                <div class="desc">我是组件描述</div>
                <div class="total-box">
                    <p>总量：<span>1020</span></p>
                    攻击成功数量102（10%）
                </div>
            </div>

            <div class="content">
                <div class="small-title">1.3 echarts图表</div>
                <div class="desc">我是组件描述</div>
                <div class="echarts-box">
                    <img src="echarts.png"/>
                </div>
            </div>

            <div class="content">
                <div class="small-title">1.4 表格</div>
                <div class="desc">我是组件描述</div>
                <table class="table">
                    <thead>
                    <tr>
                        <th>接口名称</th>
                        <th>状态</th>
                        <th>类型</th>
                        <th>链路模式</th>
                        <th>接受流量</th>
                    </tr>
                    </thead>
                    <tbody>
                    <tr>
                        <td>ens132</td>
                        <td>连接中</td>
                        <td>电口</td>
                        <td>1000 Mbps</td>
                        <td>131.21 kbps</td>
                    </tr>
                    <tr>
                        <td>ens133</td>
                        <td>连接中</td>
                        <td>电口</td>
                        <td>1000 Mbps</td>
                        <td>231.21 kbps</td>
                    </tr>
                    <tr>
                        <td>ens134</td>
                        <td>连接中</td>
                        <td>电口</td>
                        <td>1000 Mbps</td>
                        <td>631.21 kbps</td>
                    </tr>
                    </tbody>
                </table>
            </div>

            <div class="content">
                <div class="small-title">1.5 文字建议</div>
                <div class="text">
                    <p>1、公网网络边界部署抗DDOS、防火墙、WAF类网络防护设备，并将需要保护的资产添加至安全防护策略中，若已部署该类设备，请检查覆盖范围是否已涵盖所有资产。</p>
                    <p>2、内网网络边界增加内部隔离机制，发现横向攻击后能及时将恶意软件、恶意行为进行拦截，阻止内部威胁的横向传播。</p>
                    <p>3、办公终端网络建议部署防病毒、EDR、邮件安全网关类设备，降低被病毒感染、邮件钓鱼等网络攻击成功机率。</p>
                    <p>4、定期进行安全基线检查及漏洞扫描检查，及时修复安全漏洞，避免被黑客利用系统漏洞进行攻击。</p>
                    <p>5、制定并执行资产上架规范，控制业务开放端口及账号权限，定期对资产进行梳理，杜绝法外资产情况。</p>
                    <p>6、制定并执行应急响应机制，及时感知、处理、响应安全事件。</p>
                </div>
            </div>

        </div>
    </div>
</div>
</body>

</html>