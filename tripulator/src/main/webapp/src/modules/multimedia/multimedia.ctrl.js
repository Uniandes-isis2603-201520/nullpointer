(function (ng){
    var mod =ng.module("multimediaModule");
    
    mod.controller('multimediaCtrl', ['$scope', function ($scope) {
        
        $scope.photos = [
            {src: 'http://blogs-images.forbes.com/adamhartung/files/2014/08/Tropical-Vacation.jpg', index: 0},
            {src: 'http://thenextweb.com/wp-content/blogs.dir/1/files/2011/11/san-francisco.jpg', index: 1},
            {src: 'https://media-cdn.tripadvisor.com/media/photo-s/03/9b/2d/f2/new-york-city.jpg', index: 2},
            {src: 'http://cache-graphicslib.viator.com/graphicslib/thumbs674x446/2484/SITours/corcovado-mountain-and-christ-redeemer-statue-half-day-tour-in-rio-de-janeiro-128058.jpg', index: 3},
            {src: 'https://images.trvl-media.com/media/content/shared/images/travelguides/destination/178281/Madrid-26512.jpg', index: 4}
        ];

        $scope.actIndex = 0;

        $scope.nextImage = function () {
            $scope.actIndex = ($scope.actIndex < $scope.photos.length - 1) ? ++$scope.actIndex : 0;
        };

        $scope.previousImage = function ()
        {
            $scope.actIndex = ($scope.actIndex > 0) ? --$scope.actIndex : $scope.photos.length - 1;
        };

        $scope.changePhoto = function(photo){
          $scope.actIndex = photo.index;  
          alert($scope.actIndex);
        };

    }])
})




