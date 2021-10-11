<#--横向单柱状图-->
{
    "color": ["rgba(0, 116, 227, 0.8)"],
    "title": {
        "text": "${title}"
    },
    "tooltip": {
         "axisPointer": {
             "type": "shadow"
         },
         "trigger": "axis"
    },
    "legend": {
       "data": ["${legend}"]
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
}
