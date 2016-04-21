(function (ng) {
    var mod = ng.module("mainApp");
    mod.service('dataSvc', function () {
        return {
            userId: "",
            tripId: ""
        };
    });
})(window.angular);

