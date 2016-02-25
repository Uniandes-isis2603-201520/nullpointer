(function (ng) {

    var mod = ng.module("itinerarioModule");

    mod.service("itinerarioService", [function () {
            var monthStart;
            var monthEnd;
            var days;
            var pages;
            var nitems;
            var startDate = new Date("2015-03-24");
            var endDate = new Date("2015-04-10");

            this.getDateBounds = function (trip) {
                return new Promise(function (resolve, reject) {
                    var array = [startDate, endDate];
                    if (array.length !== 0) {
                        resolve(array);
                    } else {
                        reject("Error occurred");
                    }
                });
            };
            this.saveTrip = function (days) {
                return new Promise(function (resolve, reject) {
                    if (days !== null) {
                        resolve("se guardó el viaje!");
                    } else {
                        reject("Error occurred");
                    }
                });
            };
            this.getTrip = function (id, showFullMonth) {
                return new Promise(function (resolve, reject) {
                    createMatrix();
                    initializeMatrixBounds(showFullMonth);
                    setCalendarOffset(monthStart);
                    initializeDaysMatrix();
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

            /**
             * Inicializa todos los valores asociados a la matriz de días.
             * @returns {undefined}
             */
            function createMatrix() {
                days = [];
                days[0] = [];
                pages = 0;
                nitems = 0;
            }
            /**
             * Inicializa lo que serían los limites del ciclo para darle los valores a la matriz.
             * @param {type} displayFullMonth
             * @returns {undefined}
             */
            function initializeMatrixBounds(displayFullMonth) {
                monthStart = new Date(startDate.getTime());
                monthEnd = new Date(endDate.getTime());
                if (displayFullMonth) {
                    monthStart.setDate(1);
                    monthEnd.setDate(1);
                    monthEnd.setMonth(monthEnd.getMonth() + 1);
                    monthEnd.setDate(monthEnd.getDate() - 1);
                } else {
                    monthStart.setDate(monthStart.getDate() - monthStart.getDay());
                    monthEnd.setDate(monthEnd.getDate() + (6 - monthEnd.getDay()));
                }
            }


            /**
             * Agrega días sin visibilidad por relleno en los meses que no comienzan desde el domingo.
             * @param {type} date
             * @returns {undefined}
             */
            function setCalendarOffset(date) {
                for (; nitems < 7; nitems++) {
                    if (nitems === monthStart.getDay()) {
                        break;
                    } else {
                        days[pages][nitems] = {
                            date: date.getTime(),
                            valid: false,
                            invisible: true
                        };
                    }
                }
            }

            /**
             * Llena de días la matriz de días.
             * @returns {undefined}
             */
            function initializeDaysMatrix() {
                var currentMonth = monthStart.getMonth();
                for (var i = monthStart; i <= monthEnd; i.setDate(i.getDate() + 1), nitems++) {

                    if (currentMonth !== i.getMonth()) {
                        currentMonth = i.getMonth();
                        nitems = 0;
                        days[++pages] = [];
                        setCalendarOffset(i);
                    }
                    days[pages][nitems] = {
                        date: i.getTime(),
                        valid: ((i - startDate) > 0 && (i - endDate) < 0),
                        invisible: false,
                        pageNum: pages,
                        itemNum: nitems,
                        city: "city"
                    };
                }
            }
        }]);


})(window.angular);

