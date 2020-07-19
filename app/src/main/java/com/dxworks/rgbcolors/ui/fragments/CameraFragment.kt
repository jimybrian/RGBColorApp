package com.dxworks.rgbcolors.ui.fragments

import android.Manifest
import android.app.KeyguardManager
import android.content.Context.FINGERPRINT_SERVICE
import android.content.Context.KEYGUARD_SERVICE
import android.content.pm.PackageManager
import android.hardware.fingerprint.FingerprintManager
import android.os.Build
import android.os.Bundle
import android.security.keystore.KeyGenParameterSpec
import android.security.keystore.KeyPermanentlyInvalidatedException
import android.security.keystore.KeyProperties
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.databinding.DataBindingUtil
import com.dxworks.rgbcolors.R
import com.dxworks.rgbcolors.databinding.FragmentCctvBinding
import com.dxworks.rgbcolors.utils.FingerprintHandler
import dagger.android.support.DaggerFragment
import java.io.IOException
import java.security.*
import java.security.cert.CertificateException
import javax.crypto.Cipher
import javax.crypto.KeyGenerator
import javax.crypto.NoSuchPaddingException
import javax.crypto.SecretKey


class CameraFragment : DaggerFragment(){

    lateinit var binding:FragmentCctvBinding

    // Declare a string variable for the key we’re going to use in our fingerprint authentication
    private val KEY_NAME = "test_key_12345678987654321"
    private var cipher: Cipher? = null
    private var keyStore: KeyStore? = null
    private var keyGenerator: KeyGenerator? = null
    private var cryptoObject: FingerprintManager.CryptoObject? = null
    private var fingerprintManager: FingerprintManager? = null
    private var keyguardManager: KeyguardManager? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_cctv, container, false)

        setupListeners()
        checkFingerPrintAvailability()
        return binding.root
    }


    fun setupListeners(){
        binding.rdFingerprint.setOnCheckedChangeListener { _, b ->
            when(b){
                true -> {

                }
                false -> {

                }
            }
        }
    }

    fun checkFingerPrintAvailability(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            keyguardManager =
                requireActivity().getSystemService(KEYGUARD_SERVICE) as KeyguardManager?
            fingerprintManager =
                requireActivity().getSystemService(FINGERPRINT_SERVICE) as FingerprintManager?

            if (!fingerprintManager!!.isHardwareDetected) {
                Toast.makeText(requireContext(), "Your device doesn't support fingerprint authentication", Toast.LENGTH_SHORT).show()
            }
            if (ActivityCompat.checkSelfPermission(
                    requireContext(),
                    Manifest.permission.USE_FINGERPRINT
                ) !== PackageManager.PERMISSION_GRANTED
            ) {
                Toast.makeText(requireContext(), "Please enable the fingerprint permission", Toast.LENGTH_SHORT).show()
            }
            if (!fingerprintManager!!.hasEnrolledFingerprints()) {
                Toast.makeText(requireContext(), "No fingerprint configured. Please register at least one fingerprint in your device's Settings", Toast.LENGTH_SHORT).show()
            }
            if (!keyguardManager!!.isKeyguardSecure) {
                Toast.makeText(requireContext(), "Please enable lockscreen security in your device's Settings", Toast.LENGTH_SHORT).show()
            } else {
                try {
                    generateKey()
                } catch (e: FingerprintException) {
                    e.printStackTrace()
                }
                if (initCipher()) {
                    cryptoObject = FingerprintManager.CryptoObject(cipher!!)
                    val helper = FingerprintHandler(requireContext())
                    helper.startAuth(fingerprintManager!!, cryptoObject)
                }
            }
        }
    }

    @Throws(FingerprintException::class)
    private fun generateKey() {
        try {
            keyStore = KeyStore.getInstance("AndroidKeyStore")
            keyGenerator = KeyGenerator.getInstance(
                KeyProperties.KEY_ALGORITHM_AES,
                "AndroidKeyStore"
            )
            keyStore?.load(null)
            keyGenerator?.init(
                KeyGenParameterSpec.Builder(
                    KEY_NAME,
                    KeyProperties.PURPOSE_ENCRYPT or
                            KeyProperties.PURPOSE_DECRYPT
                )
                    .setBlockModes(KeyProperties.BLOCK_MODE_CBC)
                    .setUserAuthenticationRequired(true)
                    .setEncryptionPaddings(
                        KeyProperties.ENCRYPTION_PADDING_PKCS7
                    )
                    .build()
            )
            keyGenerator?.generateKey()
        } catch (exc: KeyStoreException) {
            exc.printStackTrace()
            throw FingerprintException(exc)
        } catch (exc: NoSuchAlgorithmException) {
            exc.printStackTrace()
            throw FingerprintException(exc)
        } catch (exc: NoSuchProviderException) {
            exc.printStackTrace()
            throw FingerprintException(exc)
        } catch (exc: InvalidAlgorithmParameterException) {
            exc.printStackTrace()
            throw FingerprintException(exc)
        } catch (exc: CertificateException) {
            exc.printStackTrace()
            throw FingerprintException(exc)
        } catch (exc: IOException) {
            exc.printStackTrace()
            throw FingerprintException(exc)
        }
    }


    fun initCipher(): Boolean {
        cipher = try {
            Cipher.getInstance(
                KeyProperties.KEY_ALGORITHM_AES + "/"
                        + KeyProperties.BLOCK_MODE_CBC + "/"
                        + KeyProperties.ENCRYPTION_PADDING_PKCS7
            )
        } catch (e: NoSuchAlgorithmException) {
            throw RuntimeException("Failed to get Cipher", e)
        } catch (e: NoSuchPaddingException) {
            throw RuntimeException("Failed to get Cipher", e)
        }
        return try {
            keyStore!!.load(null)
            val key: SecretKey = keyStore!!.getKey(
                KEY_NAME,
                null
            ) as SecretKey
            cipher?.init(Cipher.ENCRYPT_MODE, key)
            true
        } catch (e: KeyPermanentlyInvalidatedException) {
            false
        } catch (e: KeyStoreException) {
            throw RuntimeException("Failed to init Cipher", e)
        } catch (e: CertificateException) {
            throw RuntimeException("Failed to init Cipher", e)
        } catch (e: UnrecoverableKeyException) {
            throw RuntimeException("Failed to init Cipher", e)
        } catch (e: IOException) {
            throw RuntimeException("Failed to init Cipher", e)
        } catch (e: NoSuchAlgorithmException) {
            throw RuntimeException("Failed to init Cipher", e)
        } catch (e: InvalidKeyException) {
            throw RuntimeException("Failed to init Cipher", e)
        }
    }


    private class FingerprintException(e: Exception?) :
        Exception(e)


}