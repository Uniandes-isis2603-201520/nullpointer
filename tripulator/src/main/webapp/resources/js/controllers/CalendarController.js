app.controller('CalendarController', ['$scope', '$window', 'dayInformationService', function ($scope, $window, dayInformationService) {
        var totdays = [];
        totdays[0] = [];
        //new Date(dayInformationService.init) | 
        //new Date(dayInformationService.end) |
        var startDate = new Date("2015-03-24");
        var endDate = new Date("2015-04-10");
        var npages = 0;
        var nitems = 0;
        var scrollY = 0;

        var monthStart = new Date(startDate.getTime());
        var monthEnd = new Date(endDate.getTime());
        var currentMonth = monthStart.getMonth();

        function setCalendarOffset(date) {
            for (; nitems < 7; nitems++) {
                if (nitems === monthStart.getDay()) {
                    break;
                } else {
                    totdays[npages][nitems] = {
                        date: date.getTime(),
                        valid: false,
                        invisible: true
                    };
                }
            }
        }

        function initializeMatrixBounds() {
            monthStart.setDate(1);
            monthEnd.setDate(1);
            monthEnd.setMonth(monthEnd.getMonth() + 1);
            monthEnd.setDate(monthEnd.getDate() - 1);
        }

        function initializeDaysMatrix() {
            for (var i = monthStart; i <= monthEnd; i.setDate(i.getDate() + 1), nitems++) {

                if (currentMonth !== i.getMonth()) {
                    currentMonth = i.getMonth();
                    nitems = 0;
                    totdays[++npages] = [];
                    setCalendarOffset(i);
                }
                totdays[npages][nitems] = {
                    date: i.getTime(),
                    valid: ((i - startDate) > 0 && (i - endDate) < 0),
                    invisible: false,
                    pageNum: npages,
                    itemNum: nitems
                };
            }
        }

        initializeMatrixBounds();

        setCalendarOffset(monthStart);

        initializeDaysMatrix();


        $scope.days = totdays;
        $scope.pages = npages;
        $scope.pagenum = 0;
        $scope.showDayInfo = false;
        $scope.arrowPosition = 60;
        $scope.daynames = ["Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"];
        $scope.selectedDay = {};

        $scope.increasePageNum = function () {
            if (!$scope.showDayInfo && $scope.pagenum < totdays.length - 1) {
                $scope.pagenum++;
                $window.scrollTo($window.scrollX, 0);
            } 
            else if ($scope.showDayInfo) {
                var index = $scope.selectedDay.itemNum;
                var pnum = $scope.selectedDay.pageNum;
                if (index + 1 < $scope.days[pnum].length && $scope.days[pnum][index+1].valid) {
                    $scope.selectedDay = $scope.days[pnum][index + 1];
                } 
                else if (pnum + 1 < $scope.days.length) {
                    $scope.pagenum++;
                    for(index = 0;$scope.days[$scope.pagenum][index].invisible; index++)
                        ;
                    $scope.selectedDay = $scope.days[$scope.pagenum][index];
                    scrollY  = 0;
                }
            }
        };

        $scope.decreasePageNum = function () {
            if (!$scope.showDayInfo && $scope.pagenum > 0) {
                $scope.pagenum--;
                $window.scrollTo($window.scrollX, 0);
            }
            else if ($scope.showDayInfo) {
                var index = $scope.selectedDay.itemNum;
                var pnum = $scope.selectedDay.pageNum;
                if (index - 1 >= 0 && $scope.days[pnum][index-1].valid) {
                    $scope.selectedDay = $scope.days[pnum][index - 1];
                } 
                else if (pnum - 1 >= 0) {
                    $scope.pagenum--;
                    var index = $scope.days[$scope.pagenum].length - 1;
                    $scope.selectedDay = $scope.days[$scope.pagenum][index];
                    scrollY  = 0;
                }
            }
        };

        $scope.squareBackground = function (day, highlight) {

            if (day.invisible) {
                return{
                    "background-color": "rgba(180, 209, 255, 0.1)",
                    "color": "rgba(180, 209, 255, 0)"
                };
            }

            if (!day.valid && !highlight) {
                return{
                    "background-color": "rgba(180, 209, 255, 0.5)"
                };
            } else if (!day.valid && highlight) {
                return{
                    "background-color": "rgba(180, 209, 255, 1)"
                };
            } else if (day.valid && !highlight) {
                return{
                    "background-color": "rgba(94, 168, 167, 0.5)"
                };
            } else if (day.valid && highlight) {
                return{
                    "background-color": "rgba(94, 168, 167, 1)"
                };
            }
        };

        $scope.toggleDayInfo = function (day) {

            if (!$scope.showDayInfo)
                scrollY = $window.scrollY;

            $scope.showDayInfo = !day.invisible && day.valid && !$scope.showDayInfo;
            $scope.selectedDay = day;

            if ($scope.showDayInfo)
                $window.scrollTo($window.scrollX, 0);
            else
                $window.scrollTo($window.scrollX, scrollY);
        };

    }]);