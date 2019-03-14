## Frontend

`yarn build` and scp the files to the server like this: `scp -r ./build/*
root@192.34.57.132:/var/www/html`

## Backend

`gradle distTar` to build the program. Files will be found in
`build/distributions`. Unpack the tarball using `tar -xf
swagger-spring-1.0.0.tar`. Copy the contents to the server using `scp -Cr
swagger-spring-1.0.0 root@avocado-toast.wp6.pw:/srv/`

### Service

1. Place `backend.service` in `/etc/systemd/system/`.
2. Reload services using `sudo systemctl daemon-reload`
3. Enable starting the backend on boot using `sudo systemctl enable
   backend.service`
4. Restart the backend using `sudo systemctl restart backend.service`

## Web server

This project uses NGINX as the web server. Install it using `sudo apt install
nginx`.

Copy `default` into `/etc/nginx/sites-enabled/`, replacing the existing file.

### Service

1. Enable starting the web server on boot using `sudo systemctl enable
   nginx.service`
2. Restart the web server using `sudo systemctl restart nginx.service`
