(function (ng) {
    var mod = ng.module("mainApp");
    mod.service('dataSvc', function () {
        return {
            userId: "",
            tripId: "",
            dayId: ""
        };
    });
})(window.angular);

