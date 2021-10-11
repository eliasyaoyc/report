{
    "color": ["rgba(24, 144, 255, 1)",
              "rgba(24, 144, 255, 0.8)",
              "rgba(24, 144, 255, 0.6)",
              "rgba(24, 144, 255, 0.4)",
              "rgba(24, 144, 255, 0.2)"
    ],
    "calculable": true,
    "legend": {
        "data": ${types},
        "orient": "vertical",
        "x": "left",
        "textStyle": {
            "color": "#626262",
            "fontSize": 15,
            "fontWeight": "bolder"
        }
    },
    "series": [{
        "center": ["50%", "60%"],
        "data": ${datas},
        "name": "${title}",
        "radius": "65%",
        "type": "pie",
        "avoidLabelOverlap": true,
        "label": {
            "normal": {
                "show": true,
                "position": "top",
                "textStyle": {
                    "color":"#626262",
                    "fontSize": "15"
                }
            },
            "emphasis": {
                "show": true,
                "textStyle": {
                    "fontSize": "20"
                }
            }
        },
        "labelLine": {
            "normal": {
                "show": true
            }
        }
    }],
    "title": {
        "show":false,
        "subtext": "",
        "text": "${title}",
        "x": "center",
        "textStyle": {
            "color": "#626262",
            "fontSize": 20,
            "fontWeight": "bolder"
        }
    },
    "toolbox": {
        "feature": {
            "mark": {
                "lineStyle": {
                    "color": "#1e90ff",
                    "type": "dashed",
                    "width": 2
                },
                "show": true
            }
        },
        "show": true
    },
    "tooltip": {
        "formatter": "{a} <br/>{b} : {c} ({d}%)",
        "trigger": "item"
    }
}
