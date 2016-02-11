/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
app.factory('eventsInfoService', function ($q, $http) {
    var eventos = null;

    function LoadData() {
        var defer = $q.defer();
        $http.get('src/modules/event/services/EventsArray.json').success(function (data) {
            eventos = data;
            defer.resolve();
        });
        return defer.promise;
    }

    return {
        GetData: function () {
            return eventos;
        },
        LoadData: LoadData
    }
});

