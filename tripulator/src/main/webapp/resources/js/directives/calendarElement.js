mod.directive('calendarElement',function(){
   return{
       restrict: 'E',
       scope: {
           info: '='
       },
       templateUrl: 'src/modules/calendar/directives/calendarElement.html'
   };
});