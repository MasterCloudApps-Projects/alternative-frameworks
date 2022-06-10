function fn() {
	var port = karate.properties['demo.server.port'];
	var config = {
			'targetUrlBase': 'http://127.0.0.1:' + port
    }; 
	return config;
}