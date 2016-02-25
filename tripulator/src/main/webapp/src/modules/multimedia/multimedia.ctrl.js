(function (ng){
    var mod =ng.module("multimediaModule");
    
    mod.controller('multimediaCtrl', ['$scope','multimediaService', function ($scope, svc) {
        var self=this;
        
        $scope.actIndex=0;
                
        $scope.photos=[];

        this.initPhotos = function(){
            svc.getPhotos().then(function(response){
            
            $scope.photos=response;
            
            },responseError);
        };
        
       
        function responseError(response) {
                self.showError(response.data);
        }
        

        $scope.nextImage = function () {
            $scope.actIndex = ($scope.actIndex < $scope.photos.length - 1) ? ++$scope.actIndex : 0;
        };

        $scope.previousImage = function ()
        {
            $scope.actIndex = ($scope.actIndex > 0) ? --$scope.actIndex : $scope.photos.length - 1;
        };

        $scope.changePhoto = function(photo){
          $scope.actIndex = photo.index;  
        };
        
        this.initPhotos();


    }]);
})(window.angular);




