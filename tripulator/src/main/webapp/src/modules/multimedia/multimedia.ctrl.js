(function (ng){
    var mod =ng.module("multimediaModule");
    
    mod.controller('multimediaCtrl', ['$scope','multimediaService', function ($scope, svc) {
    
        var self=this;
             
        $scope.slides=[];
        
        $scope.splitSlides=[];
        
        $scope.showAll=false;
        
        $scope.changeView=function()
        {
           $scope.showAll=!$scope.showAll;
        };

        this.initPhotos = function(){
            svc.getPhotos().then(function(response){
            
            $scope.slides=response;
            
            },responseError);
            
            svc.getSplitPhotos().then(function(response){
            
            $scope.splitSlides=response;
            System.out.println("llegamos aqui");
            },responseError);
        };
        
        this.initSplitPhotos = function(){
            
            svc.getSplitPhotos().then(function(response){
            
            $scope.splitSlides=response;
            
            },responseError);
        };
        
        
        
       
        function responseError(response) {
                self.showError(response.data);
        }
        

        $scope.currentIndex = 0;

        $scope.setCurrentSlideIndex = function (index) {
            $scope.currentIndex = index;
        };

        $scope.isCurrentSlideIndex = function (index) {
            return $scope.currentIndex === index;
        };

        $scope.prevSlide = function () {
            $scope.currentIndex = ($scope.currentIndex < $scope.slides.length - 1) ? ++$scope.currentIndex : 0;
        };

        $scope.nextSlide = function () {
            $scope.currentIndex = ($scope.currentIndex > 0) ? --$scope.currentIndex : $scope.slides.length - 1;
        };
        
        $scope.addSlide=function()
        {
            var url = prompt("Ingrese el URL de la imagen.", "");
            if (url != null) {
                
                 svc.addPhoto(url).then(function(response){
                 this.initPhotos();
                 },responseError);
            }

        };
        
        $scope.deleteSlide=function(index)
        {
            svc.deletePhoto(index).then(function(response){
            
            this.initPhotos();
            
            },responseError);
        };
        
        this.initPhotos();


    }]);
})(window.angular);




