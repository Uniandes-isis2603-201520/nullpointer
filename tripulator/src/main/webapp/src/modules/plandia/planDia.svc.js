(function (ng) {

    var mod = ng.module("planDiaModule");

    mod.service("planDiaService", [function () {
            var daySections =[];
            var events = [
                {
                    "id": 1111,
                    "title": "Caminata por la quebrada 'La Vieja'",
                    "image": "",
                    "type": "nature",
                    "start": "7:00",
                    "end": "9:00",
                    "description": "",
                    "comments": []
                },
                {
                    "id": 2222,
                    "title": "Visita a Moserrate",
                    "image": "",
                    "type": "chill",
                    "start": "10:00",
                    "end": "13:00",
                    "description": "",
                    "comments": []
                },
                {
                    "id": 3333,
                    "title": "Almuerzo en Juan Chepe",
                    "image": "",
                    "type": "food",
                    "start": "14:00",
                    "end": "15:00",
                    "description": "",
                    "comments": []
                },
                {
                    "id": 4444,
                    "title": "Museo del oro",
                    "image": "",
                    "type": "culture",
                    "start": "16:00",
                    "end": "20:00",
                    "description": "",
                    "comments": []
                }
            ];

            var halfHours = ['00:00', '00:30', '01:00', '01:30', '02:00', '02:30', '03:00', '03:30', '04:00',
                '04:30', '05:00', '05:30', '06:00', '06:30', '07:00', '07:30', '08:00', '08:30', '09:00',
                '09:30', '10:00', '10:30', '11:00', '11:30', '12:00', '12:30', '13:00', '13:30', '14:00',
                '14:30', '15:00', '15:30', '16:00', '16:30', '17:00', '17:30', '18:00', '18:30', '19:00',
                '19:30', '20:00', '20:30', '21:00', '21:30', '22:00', '22:30', '23:00', '23:30'];


            this.getEvents = function () {
                return new Promise(function (resolve, reject) {
                    if (events.length !== 0) {
                        resolve(events);
                    } else {
                        reject("Error");
                    }
                });
            };

            /**
             * Inicializa las secciones en que se divide el día.
             * @returns {undefined}
             */
            this.createSections = function () {
                var i = 0;
                while (i < halfHours.length) {
                    daySections[i] = {
                        hour: halfHours[i],
                        available: true,
                        event: null,
                        innerText: "",
                        conflict: false
                    };
                    i++;
                }
            };

            /**
             * Llena el día con los eventos.
             * @returns {undefined}
             */
            this.markOccupiedSections = function () {
                for (var j = 0; j < events.length; j++) {
                    for (var i = getSectionIndex(events[j].start); i < getSectionIndex(events[j].end); i++) {
                        daySections[i].available = false;
                        daySections[i].event = events[j];
                    }
                    var middleSection = Math.floor((getSectionIndex(events[j].start) + getSectionIndex(events[j].end)) / 2);
                    daySections[middleSection].text = events[j].title;
                }
            };

            this.getSectionIndex = function (time) {
                var components = time.split(":");
                var hour = Number(components[0]);
                var minutes = Number(components[1]);
                return minutes >= 30 ? ((hour) * 2) + 1 : (hour) * 2;
            };
            
            
            this.getEvents();

        }]);


})(window.angular);

