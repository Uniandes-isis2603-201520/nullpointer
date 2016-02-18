(function (ng) {
    var mod = ng.module("eventoModule");
    mod.service('eventosInfoService', function ($q, $http) {
        var eventos = null;

        function LoadData() {
            var defer = $q.defer();
            $http.get('src/modules/evento/services/EventosArray.json').success(function (data) {
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
        };
    });
})(window.angular);

