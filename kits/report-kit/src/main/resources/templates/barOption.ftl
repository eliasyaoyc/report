{
    "color": ["rgba(27, 144, 255, 1)"],
    "title": {
        "show":false,
        "text": "${title}",
        "textStyle": {
        "color": "#7f7f7f",
        "fontSize": 12,
        "fontWeight": "bolder"
        }
    },
    "toolbox": {
        "feature": {},
        "show": true
    },
    "tooltip": {
        "formatter": "{a}<br/>{b} : {c}",
        "show": true
    },
    "legend": {
        "show":false,
        "textStyle": {
            "color": "#f816eb",
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
            "interval": 0,
            "rotate": 25
        },
        "axisLine":{
            "lineStyle":{
                "color":"#999aa1",
                "width":2
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
                "fontWeight": "bolder"
            }
        },
        "axisLine":{
            "show":false,
            "lineStyle":{
                "color":"#7F7F7F",
                "width":"3"
            }
        },
        "axisTick": {
           "show": true
        }
    }],
    "series": [{
        "name": "${legend}",
        "type": "bar",
		"barWidth": "30%",
        "label": {
            "normal": {
                "color": "#3590ff",
                "show": true,
                "position": "top",
				"textStyle": {
					"color": "#7F7F7F",
					"fontSize": 15,
					"fontWeight": "bolder"
				}
            }
        },
        "data":${values},
        "itemStyle": "__itemStyle"
    }]
}
