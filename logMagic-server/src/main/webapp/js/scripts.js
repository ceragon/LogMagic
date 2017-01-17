function post(URL, PARAMS) {        
    var temp = document.createElement("form");        
    temp.action = URL;        
    temp.method = "post";        
    temp.style.display = "none";        
    for (var x in PARAMS) {        
        var opt = document.createElement("textarea");        
        opt.name = x;        
        opt.value = PARAMS[x];        
        // alert(opt.name)        
        temp.appendChild(opt);        
    }        
    document.body.appendChild(temp);        
    temp.submit();        
    return temp;        
}
$("#elog-query").click(function(){
	var showSize=$("#show-size").val();
	var startTime=$("#nav-start-time").val();
	var endTime=$("#nav-end-time").val();
	post("/elog/query",{
		size:showSize,
		startTime:startTime,
		endTime:endTime
	});
});
$(".form_datetime").datetimepicker({
    format: "yyyy-mm-dd HH:ii:ss",
    autoclose: true,
    todayBtn: true,
    language:"zh-CN",
    minuteStep: 5,
});