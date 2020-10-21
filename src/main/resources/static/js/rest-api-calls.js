function getCorporations() {
    var xmlhttp = new XMLHttpRequest();

    xmlhttp.onreadystatechange = function () {
        if (this.readyState == 4 && this.status == 200) {
            var countriesList = JSON.parse(this.responseText);
            // var selectedList = document.getElementById('addCorporationSelect');
            var entity = countriesList.entity;
            var i;
            for (i=0;i<entity.length;i++){
                //selectedList.innerHTML = '<option value="' + countriesList.entity[i].countryId + '">' + countriesList.entity[0].countryName + '</option>';
                $(".selectCorporation").append(new Option(entity[i].name, entity[i].name));
            }
        }
    }
    xmlhttp.open("GET","/corporations",true);
    xmlhttp.send();
}