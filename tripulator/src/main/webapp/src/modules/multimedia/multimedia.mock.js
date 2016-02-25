
(function (ng) {

    var mod = ng.module('multimediaMock', ['ngMockE2E']);


    mod.run(['$httpBackend', function ($httpBackend) {
        var ignore_regexp = new RegExp('^((?!api).)*$');
        
        
        var recordUrl = new RegExp('api/multimedia/([0-9]+)');
        
    
        var records = [
            {src: 'http://blogs-images.forbes.com/adamhartung/files/2014/08/Tropical-Vacation.jpg', id: 0},
            {src: 'http://thenextweb.com/wp-content/blogs.dir/1/files/2011/11/san-francisco.jpg', id: 1},
            {src: 'https://media-cdn.tripadvisor.com/media/photo-s/03/9b/2d/f2/new-york-city.jpg', id: 2},
            {src: 'http://cache-graphicslib.viator.com/graphicslib/thumbs674x446/2484/SITours/corcovado-mountain-and-christ-redeemer-statue-half-day-tour-in-rio-de-janeiro-128058.jpg', index: 3},
            {src: 'https://images.trvl-media.com/media/content/shared/images/travelguides/destination/178281/Madrid-26512.jpg', id: 4}
        ];

        function getQueryParams(url) {
            var vars = {}, hash;
            var hashes = url.slice(url.indexOf('?') + 1).split('&');
            for (var i = 0; i < hashes.length; i++) {
                hash = hashes[i].split('=');
                vars[hash[0]] = hash[1];
            }
            return vars;
        }

      
        $httpBackend.whenGET(ignore_regexp).passThrough();
        
    
        $httpBackend.whenGET('api/multimedia').respond(function (method, url) {
            var queryParams = getQueryParams(url);
            var responseObj = [];
            var page = queryParams.page;
            var maxRecords = queryParams.maxRecords;
            var headers = {};
            if (page && maxRecords) {
                var start_index = (page - 1) * maxRecords;
                var end_index = start_index + maxRecords;
                responseObj = records.slice(start_index, end_index);
                headers = {"X-Total-Count": records.length};
            } else {
                responseObj = records;
            }
            return [200, responseObj, headers];
        });
      
      
        $httpBackend.whenGET(recordUrl).respond(function (method, url) {
            var id = parseInt(url.split('/').pop());
            var record;
            ng.forEach(records, function (value) {
                if (value.id === id) {
                    record = ng.copy(value);
                }
            });
            return [200, record, {}];
        });    
     
     
        $httpBackend.whenPOST('api/multimedia').respond(function (method, url, data) {
            var record = ng.fromJson(data);
            record.id = Math.floor(Math.random() * 10000);
            records.push(record);
            return [201, record, {}];
        });
        
 

        $httpBackend.whenDELETE(recordUrl).respond(function (method, url) {
            var id = parseInt(url.split('/').pop());
            ng.forEach(records, function (value, key) {
                if (value.id === id) {
                    records.splice(key, 1);
                }
            });
            return [204, null, {}];
        });
        

        $httpBackend.whenPUT(recordUrl).respond(function (method, url, data) {
            var id = parseInt(url.split('/').pop());
            var record = ng.fromJson(data);
            ng.forEach(records, function (value, key) {
                if (value.id === id) {
                    records.splice(key, 1, record);
                }
            });
            return [204, null, {}];
        });

    }]);
})(window.angular);