

#ifndef CONFIG_H
#define CONFIG_H

#include <stdbool.h>

struct wgdevice;
struct wgpeer;
struct wgallowedip;

struct config_ctx {
	struct wgdevice *device;
	struct wgpeer *last_peer;
	struct wgallowedip *last_allowedip;
	bool is_peer_section, is_device_section;
};

struct wgdevice *config_read_cmd(char *argv[], int argc);
bool config_read_init(struct config_ctx *ctx, bool append);
bool config_read_line(struct config_ctx *ctx, const char *line);
struct wgdevice *config_read_finish(struct config_ctx *ctx);

#endif
