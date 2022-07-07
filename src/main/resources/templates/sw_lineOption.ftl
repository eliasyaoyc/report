
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>Awesome go-echarts</title>
    <script src="https://go-echarts.github.io/go-echarts-assets/assets/echarts.min.js"></script>
</head>

<body>



<style> .container {display: flex;justify-content: center;align-items: center;} .item {margin: auto;} </style>
<div class="container">
    <div class="item" id="GMTToaYRKrDG" style="width:900px;height:500px;"></div>
</div>

<script type="text/javascript">
    "use strict";
    let goecharts_GMTToaYRKrDG = echarts.init(document.getElementById('GMTToaYRKrDG'), "white");
    let option_GMTToaYRKrDG = {
            "color": ["rgba(0, 116, 227, 0.8)"],
            "title": {
                "text": "${title}",
                "subtext": ""
            },
            "tooltip": {
                "show":false
            },
            "legend": {
                "show":false
            },
            "xAxis": [{
                "data": ${category}
            }],
            "yAxis": [{
            }],
            "series": [{
                "name": "${legend}",
                "type": "line",
                "stack": "总量",
                "waveAnimation":false,
                "renderLabelForZeroData":false,
                "selectedMode":false,
                "animation":false,
                "label": {
                    "normal": {
                        "color": "#666666",
                        "show": true,
                        "position": "top"
                    }
                },
                "data": ${values},
            }],
            "grid": {
                "left": "3%",
                "bottom": "3%",
                "right": "4%",
                "containLabel": true
            }
        }


    ;
    goecharts_GMTToaYRKrDG.setOption(option_GMTToaYRKrDG);
</script>




</body>
</html>
