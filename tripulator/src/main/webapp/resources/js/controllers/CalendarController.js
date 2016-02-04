app.controller('CalendarController', ['$scope', 'dayInformationService', function ($scope, dayInformationService) {
        var totdays = [];
        totdays[0] = [];
        //new Date(dayInformationService.init) | 
        //new Date(dayInformationService.end) |
        var startDate = new Date("2015-03-24");
        var endDate =  new Date("2015-04-10");
        var npages = 0;
        var nitems = 0;

        var monthStart = new Date(startDate.getTime());
        var currentMonth = monthStart.getMonth();
        
        monthStart.setDate(1);
        
        for(var i = monthStart; i <= endDate; i.setDate(i.getDate() + 1), nitems++){

            if(currentMonth !== i.getMonth())  {
                currentMonth = i.getMonth();
                nitems=0;
                totdays[++npages] = [];
            }
            totdays[npages][nitems] = {
                date: i.getTime(),
                valid: ((i-startDate) > 0)
            };
        }

        $scope.days = totdays;
        $scope.pages = npages;
        $scope.pagenum = 0;
        
        $scope.increasePageNum = function(){
            $scope.pagenum++;
        };
        $scope.decreasePageNum = function(){
            $scope.pagenum--;
        };
        
        $scope.squareBackground = function(day, highlight){
            if(!day.valid && !highlight){
                return{
                    "background-color" : "rgba(94, 168, 167, 0.5)"
                };
            }
            else if(!day.valid && highlight){
                return{
                    "background-color" : "rgba(94, 168, 167, 1)"
                };
            }
            else if(day.valid && !highlight){
                return{
                    "background-color": "rgba(180, 209, 255, 0.5)"
                };
            }
            if(day.valid && highlight){
                return{
                    "background-color": "rgba(180, 209, 255, 1)"
                };
            }
        };
}]);