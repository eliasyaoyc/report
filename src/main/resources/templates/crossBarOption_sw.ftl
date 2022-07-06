
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
      "show": false
    },
    "tooltip": {
      "axisPointer": {
        "type": "shadow"
      },
      "trigger": "axis"
    },
    "legend": {
      "show": false
    },
    "grid": {
      "left": "3%",
      "bottom": "3%",
      "right": "4%",
      "containLabel": true
    },
    "xAxis": {
      "type": "value",
      "boundaryGap": [0,0.01]
    },

    "yAxis": {
      "type": "category",
      "data": ${category}
    },
    "series": {
      "label":{
        "normal":{
          "color":"#64e8ff",
          "show":true,
          "position":"right",
          "textStyle":{
            "color":"#7F7F7F",
            "fontSize":15,
            "fontWeight":"bolder"
          }
        }
      },
      "name": "${legend}",
      "data": ${values},
      "type": "bar",
      "barWidth": "30%"
    }
  };
  goecharts_GMTToaYRKrDG.setOption(option_GMTToaYRKrDG);
</script>




</body>
</html>
