#!/bin/bash

# Pfad zum Backup-Verzeichnis
BACKUP_DIR="/home/balint/htl/4bhif/syp/2324-4bhif-syp-project-iot-dashboard/influxdb_backup"

# Anzahl der Tage, die Backups behalten werden sollen
RETENTION_DAYS=30

# Auf Fehler prüfen und gegebenenfalls abbrechen
set -e

# Alte Backups löschen
find "$BACKUP_DIR" -type d -mtime +"$RETENTION_DAYS" -exec rm -rf {} \;

echo "$(date) - Alte Backups älter als $RETENTION_DAYS Tage in $BACKUP_DIR gelöscht."