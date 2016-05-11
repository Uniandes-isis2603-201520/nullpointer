(function (ng) {
    var mod = ng.module("mainApp");
    mod.service('DiaEventoSvc', function () {
        return {
            idsEventos: [],
            ciudad: "",
            dia:""
        };
    });
})(window.angular);