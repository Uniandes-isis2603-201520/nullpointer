(function (ng) {

    var mod = ng.module("itinerarioModule");

    mod.service("itinerarioService", [function () {
            /**
             * Simula lo que trae el backend.
             * @param {type} days
             * @param {type} startDate
             * @param {type} endDate
             * @returns {undefined}
             */
            function generateDays(days, startDate, endDate) {
                var i = new Date(startDate);
                while (i.getTime() !== endDate.getTime()) {
                    days.push(
                            {
                                date: i,
                                city: "none",
                                events: [],
                                valid: true
                            }
                    );
                    var j = new Date(i);
                    j.setDate(j.getDate() + 1);
                    i = j;
                }
            }
            /**
             * Le da formato a la entrada recibida por el backend.
             * Crea una matriz donde las filas son meses y las columnas dias de ese mes.
             * Si el mes no comienza en domingo, se le agrega un relleno a la matriz.
             * @param {type} days
             * @returns {undefined}
             */
            function formatDays(days) {
                var formattedDays = [];
                for (var i = 0; i < days.length; ) {
                    var daysInMonth = [];
                    var monthStart = days[i].date;
                    setCalendarOffset(daysInMonth, monthStart);
                    while (i < days.length && days[i].date.getMonth() === monthStart.getMonth()) {
                        daysInMonth.push(days[i]);
                        i++;
                    }
                    formattedDays.push(daysInMonth);
                }
                return formattedDays;
            }
            /**
             * Agrega días invalidos al principio del mes si este no empieza en domingo.
             * @param {type} array
             * @param {type} monthStart
             * @returns {undefined}
             */
            function setCalendarOffset(array, monthStart) {
                for (var i = 0; i < monthStart.getDay(); i++) {
                    var newDate = new Date(monthStart);
                    newDate.setDate(monthStart.getDate() - (monthStart.getDay() - i));
                    array.push({
                        date: newDate,
                        valid: false
                    });
                }
            }

            this.saveTrip = function (days) {
                return new Promise(function (resolve, reject) {
                    if (days !== null) {
                        resolve("se guardó el viaje!");
                    } else {
                        reject("Error occurred");
                    }
                });
            };
            this.getTrip = function (id) {
                return new Promise(function (resolve, reject) {
                    var days = [];
                    var startDate = new Date("2015-03-24");
                    var endDate = new Date("2015-04-10");
                    generateDays(days, startDate, endDate);
                    days = formatDays(days);
                    if (id !== null) {
                        resolve(days);
                    } else {
                        reject("Error occurred");
                    }
                });
            };
            this.updateDayEvents = function (day) {
                //Se agrega a la base de datos el cambio.
                return new Promise(function (resolve, reject) {
                    if (day !== null) {
                        resolve(day);
                    } else {
                        reject("Oops! Couldn't update!");
                    }
                });
            };
            this.getDayEvent = function (day) {
                //Se agrega a la base de datos el cambio.
                return new Promise(function (resolve, reject) {
                    if (day !== null) {
                        resolve(day);
                    } else {
                        reject("Oops! Couldn't get!");
                    }
                });
            };
        }]);


})(window.angular);

