(function (ng) {
    var mod = ng.module("itinerarioModule");
    mod.service('dayInformationService', [function () {
            return {
                init: 0,
                end: 0
            };
        }]);
})(window.angular);