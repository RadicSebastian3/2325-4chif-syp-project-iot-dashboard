# Variablen
BACKUP_DIR="/home/balint/htl/4bhif/syp/2324-4bhif-syp-project-iot-dashboard/influxdb_backup"  # Lokaler Speicherort für Backups
CONTAINER_NAME="influxdb"     # Name deines InfluxDB-Containers
TIMESTAMP=$(date +"%Y%m%d_%H%M%S")  # Zeitstempel für eindeutige Backups
BACKUP_PATH="${BACKUP_DIR}/backup_${TIMESTAMP}"
INFLUX_TOKEN="8e447b3e8bbce7d5ceb9321937661420d0503a03361157018e9ec99c9ea251b4"  # Dein Admin-Token

# Verzeichnisse erstellen, falls sie nicht existieren
mkdir -p "$BACKUP_DIR"

# Backup im Container erstellen
docker exec -e INFLUX_TOKEN="$INFLUX_TOKEN" "$CONTAINER_NAME" influx backup /var/lib/influxdb/backup

# Backup auf den Host kopieren
docker cp "${CONTAINER_NAME}:/var/lib/influxdb/backup" "$BACKUP_PATH"

# Backup im Container löschen, um Speicherplatz zu sparen
docker exec "$CONTAINER_NAME" rm -rf /var/lib/influxdb/backup

# Erfolgsmeldung
echo "Backup erfolgreich erstellt unter: $BACKUP_PATH"
