
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
            "color": ["rgba(24, 144, 255, 1)",
                "rgba(24, 144, 255, 0.8)",
                "rgba(24, 144, 255, 0.6)",
                "rgba(24, 144, 255, 0.4)",
                "rgba(24, 144, 255, 0.2)"
            ],
            "legend": {
                "show":false
            },
            "series": [{
                "center": ["50%", "60%"],
                "data": ${datas},
                "name": "${title}",
                "type": "pie",
                "waveAnimation":false,
                "renderLabelForZeroData":false,
                "selectedMode":false,
                "animation":false,
                "label": {
                    "normal": {
                        "show": true,
                        "position": "top",
                        "textStyle": {
                            "color":"#626262",
                            "fontSize": "15"
                        }
                    }
                }
            }],
            "title": {
                "show":true,
                "subtext": "",
                "text": "${title}",
                "x": "center",
                "textStyle": {
                    "color": "#626262",
                    "fontSize": 20,
                    "fontWeight": "bolder"
                }
            },
            "tooltip": {
                "show":false
            }
        }

    ;
    goecharts_GMTToaYRKrDG.setOption(option_GMTToaYRKrDG);
</script>




</body>
</html>
