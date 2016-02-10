app.controller('PhotoGalleryController', ['$scope', function ($scope) {
        $scope.photos = [
            {src: 'http://blogs-images.forbes.com/adamhartung/files/2014/08/Tropical-Vacation.jpg', date: new Date(2015, 12, 1),
                desc: 'Disfrute un dia muy lindo en la playa.', place: 'Hawai', index: 0},
            {src: 'http://thenextweb.com/wp-content/blogs.dir/1/files/2011/11/san-francisco.jpg', date: new Date(2015, 12, 2),
                desc: 'No hice mucho en realidad.', place: 'San Francisco', index: 1},
            {src: 'https://media-cdn.tripadvisor.com/media/photo-s/03/9b/2d/f2/new-york-city.jpg', date: new Date(2015, 12, 3),
                desc: 'Nunca la he pasado tan bien en una ciudad.', place: 'New York', index: 2},
            {src: 'http://cache-graphicslib.viator.com/graphicslib/thumbs674x446/2484/SITours/corcovado-mountain-and-christ-redeemer-statue-half-day-tour-in-rio-de-janeiro-128058.jpg', date: new Date(2015, 12, 4),
                desc: 'Muy hermoso.', place: 'Rio de Janeiro', index: 3},
            {src: 'https://images.trvl-media.com/media/content/shared/images/travelguides/destination/178281/Madrid-26512.jpg', date: new Date(2015, 12, 5),
                desc: 'No fue tan chevere.', place: 'Madrid', index: 4}
        ];

        $scope.index = 0;

        $scope.nextImage = function () {
            $scope.index = ($scope.index < $scope.photos.length - 1) ? ++$scope.index : 0;
        };

        $scope.previousImage = function ()
        {
            $scope.index = ($scope.index > 0) ? --$scope.index : $scope.photos.length - 1;
        };

        $scope.changePhoto = function(photo){
          $scope.index = photo.index;  
          alert($scope.index);
        };

    }])
