<%@page pageEncoding="UTF-8" isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="path" value="${pageContext.request.contextPath}"/>
<script src="${path}/bootstrap/js/jquery.min.js"></script>
<script src="${path}/js/echarts.js"></script>
<script type="text/javascript" src="https://cdn.goeasy.io/goeasy-1.0.5.js"></script>

<script type="text/javascript">

    var myChart = echarts.init(document.getElementById('main'));
    $(function () {
        var option = {};
        $.ajax({
            url: "${path}/user/statistics",
            type: 'post',
            dataType: 'json',
            success: function (result) {
                option = {
                    title: {
                        text: 'xxxx活跃用户'
                    },
                    tooltip: {},
                    legend: {
                        data: ['活跃用户']
                    },
                    xAxis: {
                        data: ["一周", "两周", "三周", "四周"]
                    },
                    yAxis: {},
                    series: [{
                        name: '活跃用户',
                        type: 'bar',
                        data: result
                    }
                    ]
                };

                // 使用刚指定的配置项和数据显示图表。
                myChart.setOption(option);
            }
        });
        var goEasy = new GoEasy({
            host: 'hangzhou.goeasy.io',//应用所在的区域地址，杭州：hangzhou.goeasy.io，新加坡：singapore.goeasy.io
            appkey: "BC-f16559270b814febae93dd5d67f5be90",//替换为您的应用appkey
            forceTLS: false, //如果需要使用HTTPS/WSS，请设置为true，默认为false
            onConnected: function () {
                console.log('连接成功！')
            },
            onDisconnected: function () {
                console.log('连接断开！')
            },
            onConnectFailed: function (error) {
                console.log('连接失败或错误！')
            }
        });
        this.$goEasy.subscribe({
            channel: "186-yingxChannel",//替换为您自己的channel
            onMessage: function (message) {
                alert("Channel:" + message.channel + " content:" + message.content);
                var result = JSON.parse(message.content);
                option.data = result;

            }
        });
    });


    /*  var goEasy = new GoEasy({
      appkey: "BC-f16559270b814febae93dd5d67f5be90"
      });
      goEasy.subscribe({
      channel: "186-yingxChannel",
      onMessage: function (message) {
      $.ajax({
      url:"${path}/user/statistics",
                dataType:'json',
                success:function (result) {
                myChart.setOption({
                series:[{
                data:result
                }]
                });
                }
                });
                }
                });*/

</script>

<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Document</title>
</head>
<body>
<div align="center">

    <!-- 为 ECharts 准备一个具备大小（宽高）的 DOM -->
    <div id="main" style="width: 600px;height:400px;"></div>

</div>
</body>
</html>