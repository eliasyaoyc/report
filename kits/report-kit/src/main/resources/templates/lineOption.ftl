{
    "color": ["rgba(0, 116, 227, 0.8)"],
    "title": {
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
            }
        },
        "axisLine":{
            "lineStyle":{
                "color":"#315070",
                "width":4
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
                "color":"#315070",
                "width":4
            }
        }
    }],
    "series": [{
        "smooth": true,
        "dataFilter": "nearest",
        "lineStyle": {
            "color": "blue",
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
                "color": "green",
                "show": true,
                "position": "right"
            }
        },
        "data": ${values}
    }]
}
