{
    "title": {
        "text": "${title}",
        "textStyle": {
            "color": "#7f7f7f",
            "fontSize": 12,
            "fontWeight": "bolder"
        }
    },
    "tooltip": {
        "formatter": "{a}<br/>{b} : {c}",
        "show": true
    },
    "legend": {
        "textStyle": {
            "color": "#333333",
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
                "fontWeight": "bolder",
				"color": "#7F7F7F"
            }
        },
        "axisLine":{
            "lineStyle":{
                "color": "#7F7F7F",
                "width":"3"
            }
        },
        "data": ${category}

    }],
    "yAxis": [{
        "type": "value",
            "axisLabel": {
                "show": true,
                "textStyle": {
                    "fontSize": 15,
                    "fontWeight": "bolder",
					"color":"#7F7F7F"
                }
            },
        "axisLine":{
            "lineStyle":{
                "color":"#7F7F7F",
                "width":"3"
            }
        }
    }],
    "series": [{
        "name": "${legend}",
        "type": "bar",
        "barWidth": "30%",
        "label": {
            "normal": {
                "color": "#3370FE",
                "fontSize": 15,
                "fontWeight": "bolder",
                "show": true,
                "position": "top"
            }
        },
		"itemStyle": {
           "color":"#3370FE"
        },
		"data":${values}
    }]
}
