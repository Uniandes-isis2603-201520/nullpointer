app.controller('CalendarController', ['$scope', 'dayInformationService', function ($scope, dayInformationService) {
        var dayinms = parseInt(1000*60*60*24,10,10);
        var totdays = [];
        var start = parseInt(dayInformationService.init,10);
        var end = parseInt(dayInformationService.end,10);
        var npages = -1;
        var nitems = 0;
        for(var i = start; i <= end; i+=dayinms, nitems++){
            var index = nitems%12;
            if(index === 0)  {
                npages++;
                totdays[npages] = [];
            }
            totdays[npages][index] = i;
        }

        $scope.init = start;
        $scope.fin = end;
        $scope.days = totdays;
        $scope.pages = npages;
        $scope.pagenum = 0;
        $scope.increasePageNum = function(){
            $scope.pagenum++;
        };
        $scope.decreasePageNum = function(){
            $scope.pagenum--;
        };
}]);