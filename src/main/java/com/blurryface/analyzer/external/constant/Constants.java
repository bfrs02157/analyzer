package com.blurryface.analyzer.external.constant;

public interface Constants {
    public interface TemplateType{
        public static final String WHATSAPP_MEDIA_TYPE="mediatemplate";
        public static final String WHATSAPP_TEXT_TYPE="text";
        public static final String WHATSAPP_REPLY_TYPE="reply";
        public static final String WHATSAPP_TEMPLATE_TYPE="template";
    }
    interface KaleyraParams{
        public static final String FROM="from";
        public static final String TO="to";
        public static final String CHANNEL="channel";
        public static final String TEMPLATE_NAME="template_name";
        public static final String CALLBACK_URL="callback_url";
        public static final String CALLBACK="callback";
        public static final String TYPE="type";
        public static final String SENDER="sender";
        public static final String SOURCE="source";
        public static final String MESSAGE_IDS="message_ids";
        public static final String BODY="body";
        public static final String PARAMS="params";
        public static final String PARAM_URL="param_url";
        public static final String FOOTER="footer";
        public static final String ACTION="action";
        public static final String TRACKING_ID="tracking_id";
        public static final String TEMPLATE_ID="template_id";
        public static final String MEDIA_URL="media_url";
        public static final String PARAM_HEADER="param_header";
    }
    interface KaleyraHeaders{
        public static final String API_KEY="api-key";
        public static final String CONTENT_TYPE="Content-Type";
    }
    interface ContentType{
        public static final String MULTIPART_FORM_DATA="multipart/form-data";
        public static final String APPLICATION_JSON="application/json";
    }
}
