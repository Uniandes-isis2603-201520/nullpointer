//Data
var cities = [
    {
        city: 'Bogota',
        lat: 4.9415291,
        long: -73.9608344
    },
    {
        city: 'Cartagena',
        lat: 10.3910538,
        long: -75.4816143
    },
    {
        city: 'Medellin',
        lat: 6.2708078,
        long: -75.5678853
    },
    {
        city: 'Armenia',
        lat: 4.538418,
        long: -75.7710806
    },
    {
        city: 'Cali',
        lat: 3.4210372,
        long: -76.6226184
    }
];

//Angular App Module and Controller
var sampleApp = angular.module('mapsApp', []);
sampleApp.controller('MapController', function ($scope) {

    var mapOptions = {
        zoom: 6,
        center: new google.maps.LatLng(6.2, -75),
        mapTypeId: google.maps.MapTypeId.ROADMAP,
       
       
    }

    $scope.map = new google.maps.Map(document.getElementById('map'), mapOptions);

    $scope.markers = [];

    var infoWindow = new google.maps.InfoWindow();

    var createMarker = function (info) {

        var marker = new google.maps.Marker({
            map: $scope.map,
            position: new google.maps.LatLng(info.lat, info.long),
            title: info.city
        });

        google.maps.event.addListener(marker, 'click', function () {
            infoWindow.setContent('<h2>' + marker.title);
            infoWindow.open($scope.map, marker);
        });

        $scope.markers.push(marker);

    }

    for (i = 0; i < cities.length; i++) {
        createMarker(cities[i]);
    }

    $scope.openInfoWindow = function (e, selectedMarker) {
        e.preventDefault();
        google.maps.event.trigger(selectedMarker, 'click');
 
    }

});