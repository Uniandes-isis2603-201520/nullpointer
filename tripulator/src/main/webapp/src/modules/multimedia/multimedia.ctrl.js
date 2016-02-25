(function (ng){
    var mod =ng.module("multimediaModule");
    
    mod.controller('multimediaCtrl', ['$scope', function ($scope) {
        
        $scope.currentRecord = {};
        
        $scope.actIndex;
        
        $scope.records = [];


         this.createRecord = function () {
                $scope.$broadcast("pre-create", $scope.currentRecord);
                this.editMode = true;
                $scope.currentRecord = {};
                $scope.$broadcast("post-create", $scope.currentRecord);
            };

            this.editRecord = function (record) {
                $scope.$broadcast("pre-edit", $scope.currentRecord);
                return svc.fetchRecord(record.id).then(function (response) {
                    $scope.currentRecord = response.data;
                    self.editMode = true;
                    $scope.$broadcast("post-edit", $scope.currentRecord);
                    return response;
                }, responseError);
            };

            this.fetchRecords = function () {
                return svc.fetchRecords().then(function (response) {
                    $scope.records = response.data;
                    $scope.currentRecord = {};
                    self.editMode = false;
                    return response;
                }, responseError);
            };
            this.saveRecord = function () {
                return svc.saveRecord($scope.currentRecord).then(function () {
                    self.fetchRecords();
                }, responseError);
            };
            this.deleteRecord = function (record) {
                return svc.deleteRecord(record.id).then(function () {
                    self.fetchRecords();
                }, responseError);
            };

        $scope.nextImage = function () {
            $scope.actIndex = ($scope.actIndex < $scope.records.length - 1) ? ++$scope.actIndex : 0;
        };

        $scope.previousImage = function ()
        {
            $scope.actIndex = ($scope.actIndex > 0) ? --$scope.actIndex : $scope.records.length - 1;
        };

        $scope.changePhoto = function(record){
          $scope.actIndex = record.index;  
        };
        
        this.fetchRecords();

    }]);
})(window.angular);




