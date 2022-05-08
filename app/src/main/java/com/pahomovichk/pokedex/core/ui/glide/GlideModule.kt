package com.pahomovichk.pokedex.core.ui.glide

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import com.bumptech.glide.Glide
import com.bumptech.glide.Registry
import com.bumptech.glide.annotation.GlideModule
import com.bumptech.glide.module.AppGlideModule
import com.caverock.androidsvg.SVG
import java.io.InputStream

@GlideModule
class GlideModule : AppGlideModule() {

    override fun registerComponents(context: Context, glide: Glide, registry: Registry) {
        registry.register(SVG::class.java, Drawable::class.java, SvgDrawableTranscoder(context))
            .register(SVG::class.java, Bitmap::class.java, SvgBitmapTranscoder())
            .prepend(String::class.java, InputStream::class.java, SvgStringModelLoaderFactory())
            .append(InputStream::class.java, SVG::class.java, SvgInputStreamDecoder())
    }
}