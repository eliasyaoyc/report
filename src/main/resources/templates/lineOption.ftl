{
    "color": ["rgba(0, 116, 227, 0.8)"],
    "title": {
        "show":false,
        "text": "${title}",
        "subtext": "",
        "textStyle": {
            "color": "blue",
            "fontSize": 15,
            "fontWeight": "bolder"
        },
        "x": "left"
    },
    "tooltip": {
        "trigger": "axis"
    },
    "legend": {
        "show":false,
        "textStyle": {
            "color": "blue",
            "fontSize": 15,
            "fontWeight": "bolder"
        },
        "data": ["${legend}"]
    },
    "xAxis": [{
        "type": "category",
        "axisLabel": {
            "show": true,
            "textStyle": {
                "fontSize": 15,
                "fontWeight": "bolder"
            },
            "interval": ${interval},
            "rotate": 25
        },
        "axisLine":{
            "lineStyle":{
                "color":"#999aa1",
                "width":2
            }
        },
        "boundaryGap": false,
        "data": ${category}
    }],
    "yAxis": [{
        "type": "value",
        "axisLabel": {
            "show": true,
            "textStyle": {
                "fontSize": 15,
                "fontWeight": "bolder"
            }
        },
        "axisLine":{
            "lineStyle":{
                "color":"#999aa1",
                "width":4
            },
            "show": false
        }
    }],
    "series": [{
        "smooth": true,
        "dataFilter": "nearest",
        "lineStyle": {
            "color": "#3590ff",
            "width": 4
        },
        "legendHoverLink": true,
        "name": "${legend}",
        "type": "line",
        "stack": "总量",
        "symbolSize": "15",
        "showAllSymbol": true,
        "label": {
            "normal": {
                "color": "#666666",
                "show": true,
                "position": "top"
            }
        },
        "data": ${values},
        "symbolSize": 5
    }],
   "grid": {
     "right": "5%"
   }
}
