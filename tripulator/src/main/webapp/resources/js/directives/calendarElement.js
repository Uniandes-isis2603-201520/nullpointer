mod.directive('calendarElement',function(){
   return{
       restrict: 'E',
       scope: {
           info: '='
       },
       templateUrl: 'resources/js/directives/calendarElement.html'
   };
});