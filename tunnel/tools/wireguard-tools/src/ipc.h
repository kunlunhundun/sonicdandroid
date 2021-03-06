/*
 */

#ifndef IPC_H
#define IPC_H

#include <stdbool.h>

struct wgdevice;

int ipc_set_device(struct wgdevice *dev);
int ipc_get_device(struct wgdevice **dev, const char *interface);
char *ipc_list_devices(void);

#endif
