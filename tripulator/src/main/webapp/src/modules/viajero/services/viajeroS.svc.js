(function (ng) {
    var mod = ng.module("viajeroModule");
    mod.service('viajeroS', [function () {
            this.getValue1 = function () {
                return this.myValue1;
            };
            
            this.getValue2 = function () {
                return this.myValue2;
            };

            this.setValue1 = function (newValue) {
                this.myValue1 = newValue;
            };
            
            this.setValue2 = function (newValue) {
                this.myValue2 = newValue;
            };
        }]);
})(window.angular);