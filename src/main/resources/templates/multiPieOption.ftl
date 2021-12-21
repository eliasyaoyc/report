tooltip: {
trigger: 'item',
formatter: '{a} <br/>{b}: {c} ({d}%)'
},
legend: {
data: ${types}},
series: [
{
name: ${title},
type: 'pie',
selectedMode: 'single',
radius: [0, '30%'],
label: {
position: 'inner',
fontSize: 14
},
labelLine: {
show: false
},
data: ${datas}
},
{
name: ${title},
type: 'pie',
radius: ['45%', '60%'],
labelLine: {
length: 30
},
label: {
formatter: '{a|{a}}{abg|}\n{hr|}\n  {b|{b}：}{c}  {per|{d}%}  ',
backgroundColor: '#F6F8FC',
borderColor: '#8C8D8E',
borderWidth: 1,
borderRadius: 4,
rich: {
a: {
color: '#6E7079',
lineHeight: 22,
align: 'center'
},
hr: {
borderColor: '#8C8D8E',
width: '100%',
borderWidth: 1,
height: 0
},
b: {
color: '#4C5058',
fontSize: 14,
fontWeight: 'bold',
lineHeight: 33
},
per: {
color: '#fff',
backgroundColor: '#4C5058',
padding: [3, 4],
borderRadius: 4
}
}
},
data: ${subdatas}
}
]
