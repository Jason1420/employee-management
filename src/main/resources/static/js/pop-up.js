function momodal(checkInTime,checkOutTime,punchHour) {
    document.getElementById("checkInTime").innerText = checkInTime;
    document.getElementById("checkOutTime").innerText = checkOutTime;
    document.getElementById("punchHour").innerText = punchHour;
    document.getElementById("nenmodal-1").classList.toggle("active");
}